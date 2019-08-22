package com.xxxxx.devsuit.common;

import java.lang.reflect.Constructor;
import java.util.concurrent.atomic.AtomicLong;

import org.codehaus.janino.SimpleCompiler;

import com.xxxxx.devsuit.exception.ContainerBaseException;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;

public class Compiler {

	public static final String DEFAULT_PREFIX = "ComDotXxxxx";
	
	public static AtomicLong counter = new AtomicLong();
	
	public static class CompilerHolder {
		private static final Compiler compilerInstance = new Compiler();
	}
	
	public static Compiler getInstance() {
		return CompilerHolder.compilerInstance;
	}
	
	public <T> Class<T> compilerJavaCode(String className, String src) {
		
		try {
			SimpleCompiler compiler = new SimpleCompiler();
			
			compiler.cook(src);
			
			Class<T> targetType = (Class<T>) compiler.getClassLoader().loadClass(className);
			
			return targetType;
		} catch (Exception e) {
			throw new ContainerBaseException(String.format("动态编译出错，className=%s src=%s", className, src), e);
		}
	}
	
	public CtClass newClass(Class supperClass) {
		return newCtClassWithClassPath(supperClass, null);
	}
	
	public CtClass newCtClassWithClassPath(Class supperClass, String path) {
		try {
			ClassPool ctPool = new ClassPool(true);
			if (null != path) {
				ctPool.insertClassPath(path);
			} else {
				ClassPath ctPath = new ClassClassPath(this.getClass());
				ctPool.insertClassPath(ctPath);
			}
			
			String subClassName = ceateClassNameWithPath(supperClass, path);
			
			CtClass ctClass = ctPool.makeClass(subClassName);
			return ctClass;
		} catch(Exception e) {
			throw new ContainerBaseException(String.format("创建CtClass过程中出错supperClass = %s", supperClass), e);
		}
	}
	
	public Compiler methodWeave(CtClass ctClass, Class supperClass, String src) {
		try {
			ctClass.addMethod(CtNewMethod.make(src, ctClass));
		} catch (Exception e) {
			throw new ContainerBaseException(
					String.format("方法织入过程中出现错误supperClass = %s,methodDefinition = %s", supperClass, src), e);
		}
		//重复装饰
		return this;
	}	
	
	public <T> T newInstance(CtClass ctClass, Class<?>[] parameterTypes, Object[] parameters) {
		T target;
		try {
			if (parameterTypes == null || parameterTypes.length == 0) {
				target = (T) ctClass.toClass().newInstance();
			} else {
				
				Constructor constructor = ctClass.toClass().getDeclaredConstructor(parameterTypes);
				target = (T) constructor.newInstance(parameters);
			}
		} catch (Exception e) {
			throw new ContainerBaseException(String.format("构建过程中出现错误CtClass=%s", ctClass), e);
		}
		return target;
	}
	
	public static <T> String genClassName(Class<T> supperClass) {
		return ceateClassNameWithPath(supperClass, null);
	}
	
	public static <T> String ceateClassNameWithPath(Class<T> supperClass, String path) {
		StringBuilder subClassName = new StringBuilder();
		subClassName.append(null != path ? path : "com.xxxxx.compiler.").append(DEFAULT_PREFIX).append(supperClass).append(count());
		return subClassName.toString();
	}
	
	public void constructImplement(CtClass ctClass, Class supperClass, String constructor) {
		try {
			ctClass.addConstructor(CtNewConstructor.make(constructor, ctClass));
		} catch (CannotCompileException e) {
			throw new ContainerBaseException(
					String.format("构建过程中出现错误supperClass = %s, class = %s,constructorDefinition = %s", supperClass, ctClass, constructor), e);
		}
	}
	
	public void filedWeave(CtClass ctClass, String field) {
		filedWeaveWithAnnotation(ctClass, field, null);
	}
	
	public void filedWeaveWithAnnotation(CtClass ctClass, String field, String annotation) {
		try {
			CtField ctField = CtField.make(field, ctClass);
			if (null != annotation) {
				ConstPool constPool = ctClass.getClassFile().getConstPool();
				AnnotationsAttribute attributeInfo = new AnnotationsAttribute(constPool,
					AnnotationsAttribute.visibleTag);
				attributeInfo.addAnnotation(new javassist.bytecode.annotation.Annotation(annotation, constPool));
				
				ctField.getFieldInfo().addAttribute(attributeInfo);
			}
			
			ctClass.addField(ctField);
		} catch (Exception e) {
			throw new ContainerBaseException(String.format("构建field失败.field:%s,ctClass:%s", field, ctClass.getName()), e);
		}
	}
	
	public static long count() {
		return counter.getAndAdd(1L);
	}
}
