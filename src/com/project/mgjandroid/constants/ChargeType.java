package com.project.mgjandroid.constants;

import com.project.mgjandroid.R;
import com.ta.utdid2.android.utils.StringUtils;

public enum ChargeType {

	alipay("alipay", "支付宝手机支付", R.drawable.pay_icon_alipay),

//	alipay_wap("alipay_wap", "支付宝手机网页支付",R.drawable.pay_icon_alipay),
//
//	alipay_qr("alipay_qr", "支付宝扫码支付"),
//
//	alipay_pc_direct("alipay_pc_direct", "支付宝 PC 网页支付"),
//
//	bfb("bfb", "百度钱包移动快捷支付"),
//
//	bfb_wap("bfb_wap", "百度钱包手机网页支付"),
//
//	upacp("upacp", "银联全渠道支付"),
//
//	upacp_wap("upacp_wap", "银联全渠道手机网页支付"),
//
//	upacp_pc("upacp_pc", "银联 PC 网页支付"),
//
//	cp_b2b("cp_b2b", "银联企业网银支付"),
//
	wx("wx", "微信支付",R.drawable.pay_icon_wechat);
//
//	wx_pub("wx_pub", "微信公众账号支付"),
//
//	wx_pub_qr("wx_pub_qr", "微信公众账号扫码支付"),
//
//	yeepay_wap("yeepay_wap", "易宝手机网页支付"),
//
//	jdpay_wap("jdpay_wap", "京东手机网页支付"),
//
//	cnp_u("cnp_u", "应用内快捷支付（银联）"),
//
//	cnp_f("cnp_f", "应用内快捷支付（外卡）"),
//
//	applepay_upacp("applepay_upacp", "Apple Pay");

	private String value;

	private String memo;

	private int icon;

	ChargeType(String value, String memo , int icon) {
		this.value = value;
		this.memo = memo;
		this.icon = icon;
	}

	public String getValue() {
		return value;
	}

	public String getMemo() {
		return memo;
	}

	public static ChargeType findChargeTypeByValue(String value) {
		if (StringUtils.isEmpty(value)) {
			return null;
		}
		for (ChargeType type : ChargeType.values()) {
			if (type.getValue().equals(value)) {
				return type;
			}
		}
		return null;
	}

	public int getIcon() {
		return icon;
	}

}