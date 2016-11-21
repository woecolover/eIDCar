package trimps.eid.zl.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_person")
public class Person implements Serializable {

	private static final long serialVersionUID = -8119905574614721839L;

	/**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	/**
	 *载体标识
	 */
	@Column(length = 100)
	private String idcarrier;

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdcarrier() {
		return idcarrier;
	}

	public void setIdcarrier(String idcarrier) {
		this.idcarrier = idcarrier;
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

}
