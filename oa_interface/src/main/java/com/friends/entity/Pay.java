package com.friends.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

//交友项目 受邀用户
@Entity
@Table(name = "pay")
public class Pay {
	//
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	private String id;
	
	//账号
	private String account;
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	// 对应用户id
	private String username;
	// 应出勤天数
	private int attendance;
	
	//实际出勤天数
	private int actual;
	
	//缺勤天数
	private int absenteeism;
	
	//工资
	private BigDecimal wages;
	
	//应付工资
	private BigDecimal payWages;
	
	//实际工资
	private BigDecimal realWages;
	
	//第一次发金额
	private BigDecimal firstAmount;
	
	//第二次发金额
	private BigDecimal secondAmount;
	
	//报销
	private BigDecimal reimbursement;
	
	//银行卡号
	private String bankCardNumber;
	
	//绩效
	private BigDecimal achievements;
	
	//扣发
	private BigDecimal withholding;
	
	//转正1转0没转
	private int regular;
	
	//扣发原因
	private String reason;
	
	//社保
	private BigDecimal socialSecurity;
	
	//公积金
		private BigDecimal accumulationFund;
//发工资时间--===============
		private Date payrollTime;
	
	public Date getPayrollTime() {
			return payrollTime;
		}

		public void setPayrollTime(Date payrollTime) {
			this.payrollTime = payrollTime;
		}

	public BigDecimal getSocialSecurity() {
		return socialSecurity;
	}

	public void setSocialSecurity(BigDecimal socialSecurity) {
		this.socialSecurity = socialSecurity;
	}

	public BigDecimal getAccumulationFund() {
		return accumulationFund;
	}

	public void setAccumulationFund(BigDecimal accumulationFund) {
		this.accumulationFund = accumulationFund;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAttendance() {
		return attendance;
	}

	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}

	public int getActual() {
		return actual;
	}

	public void setActual(int actual) {
		this.actual = actual;
	}

	public int getAbsenteeism() {
		return absenteeism;
	}

	public void setAbsenteeism(int absenteeism) {
		this.absenteeism = absenteeism;
	}

	public BigDecimal getWages() {
		return wages;
	}

	public void setWages(BigDecimal wages) {
		this.wages = wages;
	}

	public BigDecimal getPayWages() {
		return payWages;
	}

	public void setPayWages(BigDecimal payWages) {
		this.payWages = payWages;
	}

	public BigDecimal getRealWages() {
		return realWages;
	}

	public void setRealWages(BigDecimal realWages) {
		this.realWages = realWages;
	}

	public BigDecimal getFirstAmount() {
		return firstAmount;
	}

	public void setFirstAmount(BigDecimal firstAmount) {
		this.firstAmount = firstAmount;
	}

	public BigDecimal getSecondAmount() {
		return secondAmount;
	}

	public void setSecondAmount(BigDecimal secondAmount) {
		this.secondAmount = secondAmount;
	}

	public BigDecimal getReimbursement() {
		return reimbursement;
	}

	public void setReimbursement(BigDecimal reimbursement) {
		this.reimbursement = reimbursement;
	}

	public String getBankCardNumber() {
		return bankCardNumber;
	}

	public void setBankCardNumber(String bankCardNumber) {
		this.bankCardNumber = bankCardNumber;
	}

	public BigDecimal getAchievements() {
		return achievements;
	}

	public void setAchievements(BigDecimal achievements) {
		this.achievements = achievements;
	}

	public BigDecimal getWithholding() {
		return withholding;
	}

	public void setWithholding(BigDecimal withholding) {
		this.withholding = withholding;
	}

	public int getRegular() {
		return regular;
	}

	public void setRegular(int regular) {
		this.regular = regular;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
}
