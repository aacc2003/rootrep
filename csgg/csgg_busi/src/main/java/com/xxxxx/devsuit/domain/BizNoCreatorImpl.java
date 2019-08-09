package com.xxxxx.devsuit.domain;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.util.StringUtils;

public class BizNoCreatorImpl implements BizNoCreator {
	
	private static final String FILL_STRING = "00000000";
	
	private DBPlugin dbPlugin;
	
	public BizNoCreatorImpl(DBPlugin dbPlugin) {
		if (dbPlugin == null) {
			throw new RuntimeException("初始化BizNoCreator出错，DBPlugin不能为空");
		}
		
		this.dbPlugin = dbPlugin;
	}

	@Override
	public String createBizNo(long seq, String bizPrefix) {
		if (!StringUtils.hasText(bizPrefix)) {
			throw new RuntimeException("生成bizNo出错，bizPrefix不能为空");
		}
		
		StringBuilder sb = new StringBuilder();
		String bizPrefixTmp = "0000";
		bizPrefixTmp = bizPrefixTmp+bizPrefix;
		bizPrefixTmp = bizPrefixTmp.substring(bizPrefixTmp.length()-4);
		String dt = getDate(dbPlugin);
		
		String seqTmp = FILL_STRING+seq;
		seqTmp = seqTmp.substring(seqTmp.length()-8);
		
		return sb.append(bizPrefixTmp).append(dt).append(seqTmp).toString();
	}
	
	private String getDate(DBPlugin dbPlugin) {
		StringBuilder sb = new StringBuilder();
		Calendar now = new GregorianCalendar();
		now.setTime(dbPlugin.currentTime());
		String yyyy = String.valueOf(now.get(Calendar.YEAR));
		String mm = String.valueOf(now.get(Calendar.MONTH));
		String dd = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
		String hh = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
		String mi = String.valueOf(now.get(Calendar.MINUTE));
		String ss = String.valueOf(now.get(Calendar.SECOND));
//		String ms = String.valueOf(now.get(Calendar.MILLISECOND));
		sb.append(yyyy);
		if (mm.length()<2) {
			sb.append("0");
		}
		sb.append(mm);
		if (dd.length()<2) {
			sb.append("0");
		}
		sb.append(dd);
		if (hh.length()<2) {
			sb.append("0");
		}
		sb.append(hh);
		if (mi.length()<2) {
			sb.append("0");
		}
		sb.append(mi);
		if (ss.length()<2) {
			sb.append("0");
		}
		sb.append(ss);
		
		return sb.toString();
	}

	@Override
	public String createBizNo(String seqName, String bizPrefix) {
		
		return createBizNo(getSeq(seqName), bizPrefix);
	}

	@Override
	public long getSeq(String seqName) {
		
		return dbPlugin.nextVar(seqName);
	}

}
