package com.xxxxx.copy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.cglib.beans.BeanCopier;
import org.springframework.util.Assert;

public class BeanCopyUtils {
	/**
     * 创建过的BeanCopier实例放到缓存中，下次可以直接获取，提升性能
     */
    private static final Map<String, BeanCopier> BEAN_COPIERS 
    	= new ConcurrentHashMap<String, BeanCopier>();

    /**
     * 该方法没有自定义Converter,简单进行常规属性拷贝
     * --一般类型是深拷贝
     * --对象属性A a;  方式是：只找到类型肯名字一致，浅拷贝（enum也是浅拷贝）
     *
     * @param srcObj  源对象
     * @param destObj 目标对象
     */
    public static void copy(final Object srcObj, final Object destObj) {
    	Assert.isTrue(srcObj!=null,"源对象为空");
    	Assert.isTrue(destObj!=null, "目标对象为空");
        String key = genKey(srcObj.getClass(), destObj.getClass());
        BeanCopier copier;
        if (!BEAN_COPIERS.containsKey(key)) {
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);
            BEAN_COPIERS.put(key, copier);
        } else {
            copier = BEAN_COPIERS.get(key);
        }
        copier.copy(srcObj, destObj, null);
    }

    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {
        return srcClazz.getName() + destClazz.getName();
    }
    
    
//  ---------------------  test -----------------
    enum TestEnum {
    	one, two, three;
    }
    
    class A {
    	int id;
    	String name;
    	String sex;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		@Override
		public String toString() {
			return "A [id=" + id + ", name=" + name + ", sex=" + sex + "]";
		}
    	
    }
    
    class B extends A {
    	String home;
    	boolean isNiceMan;
    	TestEnum en;
		public String getHome() {
			return home;
		}
		public void setHome(String home) {
			this.home = home;
		}
		public boolean isNiceMan() {
			return isNiceMan;
		}
		public void setNiceMan(boolean isNiceMan) {
			this.isNiceMan = isNiceMan;
		}
		public TestEnum getEn() {
			return en;
		}
		public void setEn(TestEnum en) {
			this.en = en;
		}
		@Override
		public String toString() {
			return "B [home=" + home + ", isNiceMan=" + isNiceMan + ", en=" + en + ", toString()=" + super.toString()
					+ "]";
		}
    	
    }
    
    class C {
    	B b;
    	int hight;
    	Integer weight;
		public B getB() {
			return b;
		}
		public void setB(B b) {
			this.b = b;
		}
		public int getHight() {
			return hight;
		}
		public void setHight(int hight) {
			this.hight = hight;
		}
		public Integer getWeight() {
			return weight;
		}
		public void setWeight(Integer weight) {
			this.weight = weight;
		}
		@Override
		public String toString() {
			return "C [b=" + b.toString() + ", hight=" + hight + ", weight=" + weight + "]";
		}
    	
    }
    
    public static void main(String[] args) {
    	BeanCopyUtils bcu = new BeanCopyUtils();
    	BeanCopyUtils.A a = bcu. new A();
    	BeanCopyUtils.B b = bcu. new B();
    	b.setEn(TestEnum.three);
    	b.setHome("CQ");
    	b.setId(2);
    	b.setName("shanshan");
    	b.setNiceMan(true);
    	b.setSex("femal");
    	
    	BeanCopyUtils.C c = bcu. new C();
    	c.setB(b);
    	c.setHight(1);
    	c.setWeight(33);
    	
    	BeanCopyUtils.C c2 = bcu. new C();
    	BeanCopyUtils.copy(c, c2);
    	System.out.println(c2.toString());
    	
    	BeanCopyUtils.B b2 = bcu. new B();
    	BeanCopyUtils.copy(c.getB(), b2);
    	System.out.println(b2.toString());
    	
    	BeanCopyUtils.copy(b2, a);
    	System.out.println(a.toString());
    	
    	// -- 改变b2属性，是否是浅拷贝 （对于对象类型属性是浅拷贝）
    	System.out.println("----------------------------------------");
    	b2.setName("dingding"); // -- A的name属性并没有改成dingding， 因为是一般属性更改
    	c2.getB().setEn(TestEnum.one);
    	c2.getB().setName("dingding"); // --全改了:c2获取B的引用，是浅拷贝
    	System.out.println(b2.toString());
    	System.out.println(a.toString());
    	System.out.println(b.toString());
    	System.out.println(c.toString());
    	System.out.println(c2.toString());
	}
}
