package com.project.mgjandroid.model;

/**
 * Created by ning on 2016/4/6.
 */
public class RechargeModel extends Entity {

    /**
     * code : 0
     * uuid : A00000552D1815
     * value : _input_charset="UTF-8"&body="充值50元"&it_b_pay="1h"&notify_url="http://120.24.16.64/merchant/alipayNotifies/backend"&out_trade_no="1604060000003005"&partner="2088811120305454"&pay_method="directPay"&payment_type="1"&seller_id="2088811120305454"&service="mobile.securitypay.pay"&subject="交易-1604060000003005"&total_fee="50"&sign_type="RSA"&sign="KsY3QnQA6Rthk6GfiN96kQJPtBtCG%2BhhYOt%2BKajOFcZrT4qKOsdkLPNDsadZWbTm%2F4GY9gbdZBMxE0lpl%2FJY10vErLwW8wv4dzHVWhYcxmT3p4B96nfPe%2BvAknaWMXeyCJvv3vBhuSwCzvm%2B3GcrP7IYnaPGBISmKyz57kt77Xw%3D"
     * success : true
     * servertime : 2016-04-06 17:24:59
     */

    private int code;
    private String uuid;
    private String value;
    private boolean success;
    private String servertime;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getServertime() {
        return servertime;
    }

    public void setServertime(String servertime) {
        this.servertime = servertime;
    }
}
