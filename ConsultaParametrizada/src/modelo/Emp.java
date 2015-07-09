package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the emp database table.
 * 
 */
//@Entity
//@Table(name="emp")
//@NamedQuery(name="Emp.findAll", query="SELECT e FROM Emp e")
public class Emp implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(unique=true, nullable=false, precision=10)
	private long empno;

//	@Column(precision=10, scale=2)
	private BigDecimal comm;

//	@Column(length=10)
	private String ename;

//	@Temporal(TemporalType.DATE)
	private Date hiredate;

//	@Column(length=9)
	private String job;

//	@Column(precision=10, scale=2)
	private BigDecimal sal;

	//bi-directional many-to-one association to Dept
//	@ManyToOne
//	@JoinColumn(name="deptno")
	private Dept dept;

	//bi-directional many-to-one association to Emp
//	@ManyToOne
//	@JoinColumn(name="mgr")
	private Emp emp;

	//bi-directional many-to-one association to Emp
//	@OneToMany(mappedBy="emp")
	private List<Emp> emps;

	public Emp() {
	}

	public long getEmpno() {
		return this.empno;
	}

	public void setEmpno(long empno) {
		this.empno = empno;
	}

	public BigDecimal getComm() {
		return this.comm;
	}

	public void setComm(BigDecimal comm) {
		this.comm = comm;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public Date getHiredate() {
		return this.hiredate;
	}

	public void setHiredate(Date hiredate) {
		this.hiredate = hiredate;
	}

	public String getJob() {
		return this.job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public BigDecimal getSal() {
		return this.sal;
	}

	public void setSal(BigDecimal sal) {
		this.sal = sal;
	}

	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Emp getEmp() {
		return this.emp;
	}

	public void setEmp(Emp emp) {
		this.emp = emp;
	}

	public List<Emp> getEmps() {
		return this.emps;
	}

	public void setEmps(List<Emp> emps) {
		this.emps = emps;
	}

	public Emp addEmp(Emp emp) {
		getEmps().add(emp);
		emp.setEmp(this);

		return emp;
	}

	public Emp removeEmp(Emp emp) {
		getEmps().remove(emp);
		emp.setEmp(null);

		return emp;
	}

}