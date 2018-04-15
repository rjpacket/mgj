package com.project.mgjandroid.model;

/**
 * Created by ning on 2016/3/26.
 */
public class GetAlipayInfoModel extends Entity {

    /**
     * code : 0
     * uuid : A00000552D1815
     * value : _input_charset="UTF-8"&body="2016032600002626"&it_b_pay="1h"&notify_url="http://120.24.16.64/merchant/alipayNotifies/backend"&out_trade_no="2016032600000202"&partner="2088811120305454"&pay_method="directPay"&payment_type="1"&seller_id="2088811120305454"&service="mobile.securitypay.pay"&subject="订单-2016032600002626"&total_fee="39.20"&sign_type="RSA"&sign="hqP7bX9wFwr9IyrvSWS7XCCYCGWkDZEcWxxjPi1UnpA%2FR3SCYbTBa%2FFjTuYIvkzOweiN4Jo%2FVfG9NR3OiRQ%2BiYo%2BgVrknXQ35jVJaq7%2FtTiBAhi3ptjerOvJZX2efCUyFqTOLdbF3%2BEj%2FmkpDm8yCIUeZAR2pvRDW4Rwl3V%2FnNs%3D"
     * success : true
     */

    private int code;
    private String uuid;
    private String value;
    private boolean success;

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
}
