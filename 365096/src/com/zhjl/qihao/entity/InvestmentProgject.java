package com.zhjl.qihao.entity;

import java.io.Serializable;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/***
 * 投资项目模型
 * @author south
 *
 */
@Table( name = "investmentProgject" )
public class InvestmentProgject implements Serializable{

	@Id(column = "_id")
	private int _id;
	/**标题*/
	String title;
	/**子标题*/
	String subTitle;
	/***投资人数*/
	String personNumber;
	/**年收益*/
	String annualIncome;
	/**贷款总额*/
	String totalLoans;
	/**借款期限*/
	String loanPeriod;
	/**进度 */
	String progress;
	/**状态*/
	String status;
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSubTitle() {
		return subTitle;
	}
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	public String getPersonNumber() {
		return personNumber;
	}
	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}
	public String getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}
	public String getTotalLoans() {
		return totalLoans;
	}
	public void setTotalLoans(String totalLoans) {
		this.totalLoans = totalLoans;
	}
	public String getLoanPeriod() {
		return loanPeriod;
	}
	public void setLoanPeriod(String loanPeriod) {
		this.loanPeriod = loanPeriod;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
