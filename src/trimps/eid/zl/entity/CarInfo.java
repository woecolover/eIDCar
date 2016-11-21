package trimps.eid.zl.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_car_info")
public class CarInfo implements Serializable {

	private static final long serialVersionUID = -8119905574614721839L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * 车牌号
	 */
	@Column(length = 100)
	private String car_num;

	/**
	 * 车辆RFID
	 */
	@Column(length = 100)
	private String rfid_num;

	/**
	 * 车主eID
	 */
	@Column(length = 100)
	private String car_eid;

	/**
	 * 车主姓名
	 */
	@Column(length = 100)
	private String owner_name;

	/**
	 * 车主身份证号
	 */
	@Column(length = 100)
	private String owner_num;

	/**
	 * 车主状态
	 */
	@Column(length = 100)
	private String person_state;

	/**
	 * 车主状态更新时间
	 */
	@Column(length = 100)
	private Date person_state_time;

	/**
	 * 车辆状态
	 */
	@Column(length = 100)
	private String car_state;

	/**
	 * 车辆状态更新时间
	 */
	@Column(length = 100)
	private Date car_state_time;

	/**
	 * 批次号
	 */
	@Column(length = 100)
	private String batch_num;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCar_num() {
		return car_num;
	}

	public void setCar_num(String car_num) {
		this.car_num = car_num;
	}

	public String getRfid_num() {
		return rfid_num;
	}

	public void setRfid_num(String rfid_num) {
		this.rfid_num = rfid_num;
	}

	public String getOwner_name() {
		return owner_name;
	}

	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	public String getOwner_num() {
		return owner_num;
	}

	public void setOwner_num(String owner_num) {
		this.owner_num = owner_num;
	}

	public String getPerson_state() {
		return person_state;
	}

	public void setPerson_state(String person_state) {
		this.person_state = person_state;
	}

	public Date getPerson_state_time() {
		return person_state_time;
	}

	public void setPerson_state_time(Date person_state_time) {
		this.person_state_time = person_state_time;
	}

	public String getCar_state() {
		return car_state;
	}

	public void setCar_state(String car_state) {
		this.car_state = car_state;
	}

	public Date getCar_state_time() {
		return car_state_time;
	}

	public void setCar_state_time(Date car_state_time) {
		this.car_state_time = car_state_time;
	}

	public String getBatch_num() {
		return batch_num;
	}

	public void setBatch_num(String batch_num) {
		this.batch_num = batch_num;
	}

	public String getCar_eid() {
		return car_eid;
	}

	public void setCar_eid(String car_eid) {
		this.car_eid = car_eid;
	}

}
