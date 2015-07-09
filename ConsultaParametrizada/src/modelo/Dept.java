package modelo;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the dept database table.
 * 
 */
//@Entity
//@Table(name="dept")
//@NamedQuery(name="Dept.findAll", query="SELECT d FROM Dept d")
public class Dept implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
//	@Column(unique=true, nullable=false, precision=10)
	private long deptno;

//	@Column(length=14)
	private String dname;

//	@Column(length=13)
	private String loc;

	//bi-directional many-to-one association to Emp
//	@OneToMany(mappedBy="dept")
	private List<Emp> emps;

	public Dept() {
	}

	public long getDeptno() {
		return this.deptno;
	}

	public void setDeptno(long deptno) {
		this.deptno = deptno;
	}

	public String getDname() {
		return this.dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public String getLoc() {
		return this.loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public List<Emp> getEmps() {
		return this.emps;
	}

	public void setEmps(List<Emp> emps) {
		this.emps = emps;
	}

	public Emp addEmp(Emp emp) {
		getEmps().add(emp);
		emp.setDept(this);

		return emp;
	}

	public Emp removeEmp(Emp emp) {
		getEmps().remove(emp);
		emp.setDept(null);

		return emp;
	}

}