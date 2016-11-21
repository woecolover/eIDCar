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
@Table(name = "t_car_batch")
public class CarBatch implements Serializable {

	private static final long serialVersionUID = -8119905574614721839L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 * 批次号
	 */
	@Column(length = 100)
	private String batch_num;

	/**
	 * 批次数量
	 */
	@Column(length = 100)
	private int batch_count;

	/**
	 * 批次创建时间
	 */
	@Column(length = 100)
	private Date batch_time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBatch_num() {
		return batch_num;
	}

	public void setBatch_num(String batch_num) {
		this.batch_num = batch_num;
	}

	public int getBatch_count() {
		return batch_count;
	}

	public void setBatch_count(int batch_count) {
		this.batch_count = batch_count;
	}

	public Date getBatch_time() {
		return batch_time;
	}

	public void setBatch_time(Date batch_time) {
		this.batch_time = batch_time;
	}

}
