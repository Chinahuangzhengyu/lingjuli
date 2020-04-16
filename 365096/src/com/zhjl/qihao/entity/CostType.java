/**
 * 
 */
package com.zhjl.qihao.entity;

import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author south
 *
 */
@DatabaseTable(tableName="CostType")
public class CostType implements Serializable {
	@DatabaseField(id=true,dataType=DataType.STRING,index=true,width=30)
	String costId;
	@DatabaseField(dataType=DataType.STRING,width=100)
	String costName;
	@DatabaseField(dataType=DataType.STRING,width=50)
	String costStandard;
	@DatabaseField(dataType=DataType.STRING,width=30,index=true) 
	String serviceId_fk;
	public String getCostId() {
		return costId;
	}
	public void setCostId(String costId) {
		this.costId = costId;
	}
	public String getCostName() {
		return costName;
	}
	public void setCostName(String costName) {
		this.costName = costName;
	}
	public String getCostStandard() {
		return costStandard;
	}
	public void setCostStandard(String costStandard) {
		this.costStandard = costStandard;
	}
	public String getServiceId_fk() {
		return serviceId_fk;
	}
	public void setServiceId_fk(String serviceId_fk) {
		this.serviceId_fk = serviceId_fk;
	}
	
	
}
