/**
 * 
 */
package com.zhjl.qihao.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author 
 *
 */
@DatabaseTable(tableName="ServiceType")
public class ServiceTypeBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4464545769511115322L;
	@DatabaseField(id=true,dataType=DataType.STRING,index=true,width=30)
	String serviceId;
	@DatabaseField(dataType=DataType.STRING,width=1000)
	String serviceCode;
	@DatabaseField(dataType=DataType.STRING,width=100)
	String serviceName;
	@DatabaseField(dataType=DataType.STRING,width=2)
	String serviceType;
	@DatabaseField(dataType=DataType.STRING,width=6,index=true)
	String smallCode;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSmallCode() {
		return smallCode;
	}
	public void setSmallCode(String smallCode) {
		this.smallCode = smallCode;
	}
}
