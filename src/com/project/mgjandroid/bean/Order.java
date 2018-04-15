package com.project.mgjandroid.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	/** 商家编号 */
	private Long merchantId;
	/** 用户编号 */
	private Long appUserId;
	/** 配送信息 */
	private String deliveryInfo;
	/** 配送备注 */
	private String memo;
	/** 下单时间 */
	private Date orderTime;
	/** 期望送货时间 */
	private Date expectDeliveryTime;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date modifyTime;
	/** 订单商品结合 */
	private List<OrderItem> orderItems;
	/** 订单初始价格 */
	private BigDecimal orderPrice = BigDecimal.ZERO;
	/** 订单最后价格 */
	private BigDecimal finalPrice = BigDecimal.ZERO;
	/** 配送费 */
	private BigDecimal deliverFee = BigDecimal.ZERO;
	/** 参与活动详情 */
	private String promotions;
	/** 配送类型 */
	private Integer deliveryType;
	/** 配送状态 */
	private int deliveryStatus;
	/** 支付类型 */
	private Integer payType;
	/** 支付状态 */
	private int paymentStatus;
	/** 交易编号 */
	private Long transactionId;
	/** 是否删除 */
	private int hasDel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Long merchantId) {
		this.merchantId = merchantId;
	}

	public Long getAppUserId() {
		return appUserId;
	}

	public void setAppUserId(Long appUserId) {
		this.appUserId = appUserId;
	}

	public String getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(String deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getExpectDeliveryTime() {
		return expectDeliveryTime;
	}

	public void setExpectDeliveryTime(Date expectDeliveryTime) {
		this.expectDeliveryTime = expectDeliveryTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public BigDecimal getDeliverFee() {
		return deliverFee;
	}

	public void setDeliverFee(BigDecimal deliverFee) {
		this.deliverFee = deliverFee;
	}

	public String getPromotions() {
		return promotions;
	}

	public void setPromotions(String promotions) {
		this.promotions = promotions;
	}

	public Integer getDeliveryType() {
		return deliveryType;
	}

	public void setDeliveryType(Integer deliveryType) {
		this.deliveryType = deliveryType;
	}

	public int getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(int deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public int getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(int paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public int getHasDel() {
		return hasDel;
	}

	public void setHasDel(int hasDel) {
		this.hasDel = hasDel;
	}

}
