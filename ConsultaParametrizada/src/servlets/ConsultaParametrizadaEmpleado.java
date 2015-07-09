package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/ConsultaParametrizadaEmpleado")
public class ConsultaParametrizadaEmpleado extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String vistaDestino = "/ConsultaEmpleado.jsp";
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(vistaDestino);
		dispatcher.forward(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
				
		// recepción de parámetros
		out.println("<h3>Parámetros recibidos</h3>");
		int contadorParametrosRecibidos = 0;
		
		out.println("Ename = ");
		String ename = request.getParameter("ename");
		if (ename == null) {  // qué hacer si no se recibe un parámetro llamado ename
			out.println("*** NO RECIBIDO ***");
		} else {
			out.println(ename);
			if (!ename.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		out.println(" <br />");
		
		out.println("Job = ");
		String job = request.getParameter("job");
		if (job == null) {  // qué hacer si no se recibe un parámetro llamado job
			out.println("*** NO RECIBIDO ***");
		} else {
			out.println(job);
			if (!job.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		out.println(" <br />");

		out.println("Mgr = ");
		String mgr = request.getParameter("mgr");
		if (mgr == null) {  // qué hacer si no se recibe un parámetro llamado job
			out.println("*** NO RECIBIDO ***");
		} else {
			out.println(mgr);
			if (!mgr.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		out.println(" <br />");
		
		out.println("HiredateMin = ");
		String hiredateMin = request.getParameter("hiredateMin");
		if (hiredateMin == null) {  // qué hacer si no se recibe un parámetro llamado job
			out.println("*** NO RECIBIDO ***");
		} else {
			out.println(hiredateMin);
			if (!hiredateMin.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		out.println(" <br />");
		
		out.println("HiredateMax = ");
		String hiredateMax = request.getParameter("hiredateMax");
		if (hiredateMax == null) {  // qué hacer si no se recibe un parámetro llamado job
			out.println("*** NO RECIBIDO ***");
		} else {
			out.println(hiredateMax);
			if (!hiredateMax.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		out.println(" <br />");
		
		out.println("SalMin = ");
		String salMin = request.getParameter("salMin");
		if (salMin == null) {  // qué hacer si no se recibe un parámetro llamado job
			out.println("*** NO RECIBIDO ***");
		} else {
			out.println(salMin);
			if (!salMin.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		out.println(" <br />");
		
		out.println("SalMax = ");
		String salMax = request.getParameter("salMax");
		if (salMax == null) {  // qué hacer si no se recibe un parámetro llamado job
			out.println("*** NO RECIBIDO ***");
		} else {
			out.println(salMax);
			if (!salMax.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		out.println(" <br />");
		
		out.println("Comm = ");
		String comm = request.getParameter("comm");
		if (comm == null) {  // qué hacer si no se recibe un parámetro llamado job
			out.println("*** NO RECIBIDO ***");
		} else {
			out.println(comm);
			if (!comm.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		out.println(" <br />");
		
		out.println("Deptno = ");
		String arrayDeptnos[] = request.getParameterValues("deptno[]");
		// String deptno = request.getParameter("deptno");
		if (arrayDeptnos == null) {  // qué hacer si no se recibe un parámetro llamado job
			out.println("*** NO RECIBIDO ***");
		} else {
			for (String deptno : arrayDeptnos) {
				out.println(deptno + " &emsp; ");
			}
			if (arrayDeptnos.length > 0) {
				contadorParametrosRecibidos++;
			}
		}
		out.println("<br />");

		out.println("Nº parámetros recibidos: " + contadorParametrosRecibidos + " <br />");
		
		
		out.println("<h3>Consulta generada</h3>");
		Map <String,String> condiciones = new HashMap<String, String>();
		condiciones.put("select", "SELECT e.empno, e.ename, e.job, e.mgr, e.hiredate, e.sal, e.comm, e.deptno, d.dname, d.loc ");
		condiciones.put("from", "FROM EMP e, DEPT d ");
		condiciones.put("joinWhere", "WHERE e.deptno = d.deptno ");
		condiciones.put("ename", "AND e.ename LIKE '%" + ename + "%' ");
		condiciones.put("job", "AND e.job LIKE '%" + job + "%' ");
		
		String select = "SELECT e.empno, e.ename, e.job, e.mgr, e.hiredate, e.sal, e.comm, e.deptno, d.dname, d.loc ";
		String from = "FROM EMP e, DEPT d ";
		String where = "WHERE e.deptno = d.deptno ";
		String condicionEname = "AND e.ename LIKE '%" + ename + "%' ";
		String condicionJob = "AND e.job LIKE '%" + job + "%' ";
		
		String condicionMgr = "";  // alternativa -> "AND e.mgr IS NULL"
		condiciones.put("mgr", "");
		if (!mgr.isEmpty()) {
			condicionMgr = "AND e.mgr = " + mgr + " ";
			condiciones.put("mgr", "AND e.mgr = " + mgr + " ");
		}
		
		String condicionHiredateMin = " ";  // alternativa -> "AND hiredate >= (SELECT MIN(hiredate) FROM EMP)";
		condiciones.put("hiredateMin", "");
		if (!hiredateMin.isEmpty()) {
			condicionHiredateMin = "AND e.hiredate >= '" + hiredateMin + "' ";
			condiciones.put("hiredateMin", "AND e.hiredate >= '" + hiredateMin + "' ");
		}
		
		String condicionHiredateMax = " ";  // alternativa -> "AND hiredate <= (SELECT MAX(hiredate) FROM EMP)";
		condiciones.put("hiredateMax", "");
		if (!hiredateMax.isEmpty()) {
			condicionHiredateMax = "AND e.hiredate <= '" + hiredateMax + "' ";
			condiciones.put("hiredateMax", "AND e.hiredate >= '" + hiredateMax + "' ");
		}
		
		String condicionSalMin = " ";
		condiciones.put("salMin", "");
		if (!salMin.isEmpty()) {
			condicionSalMin = "AND e.sal >= " + salMin + " ";
			condiciones.put("salMin", "AND e.sal >= " + salMin + " ");
		}
		
		String condicionSalMax = " ";
		condiciones.put("salMax", "");
		if (!salMax.isEmpty()) {
			condicionSalMax = "AND e.sal <= " + salMax + " ";
			condiciones.put("salMax", "AND e.sal >= " + salMax + " ");
		}
		
		String condicionComm = " ";
		condiciones.put("comm", "");
		if (!comm.isEmpty()) {
			condicionComm = "AND e.comm = " + comm + " ";
			condiciones.put("comm", "AND e.comm = " + comm + " ");
		}
		
		String condicionDeptnos = "";
		condiciones.put("deptno", "");
		if (arrayDeptnos!=null && arrayDeptnos.length > 0) {  // se recibió algún deptno
			condicionDeptnos = "AND e.deptno IN (";
			for (String deptno : arrayDeptnos) {
				condicionDeptnos = condicionDeptnos + deptno + ", ";
			}
			condicionDeptnos = condicionDeptnos.substring(0, condicionDeptnos.length()-2);  // se quita la última coma (,)
			condicionDeptnos = condicionDeptnos + ") ";
			condiciones.put("deptno", condicionDeptnos);
		}

		
		/*String consultaSQL = select + from  + where + condicionEname + condicionJob + condicionMgr
						+ condicionHiredateMin + condicionHiredateMax + condicionSalMin + condicionSalMax
						+ condicionComm + condicionDeptnos;
*/
		out.println("<div style=\"font-family: courier; font-size: 14px;\">");		
		out.println(select + "<br />");
		out.println(from + " <br />");
		out.println(where + " <br />");
		out.println(condicionEname + " <br />");
		out.println(condicionJob + " <br />");
		out.println(condicionMgr + " <br />");
		out.println(condicionHiredateMin + " <br />");
		out.println(condicionHiredateMax + " <br />");
		out.println(condicionSalMin + " <br />");
		out.println(condicionSalMax + " <br />");
		out.println(condicionComm + " <br />");
		out.println(condicionDeptnos + " <br />");
		out.println("</div>");

		String consultaSQLCondiciones = condiciones.get("select") + condiciones.get("from") + condiciones.get("joinWhere") +
						condiciones.get("ename") + condiciones.get("job") + condiciones.get("mgr") + 
						condiciones.get("hiredateMin") + condiciones.get("hiredateMax") + 
						condiciones.get("salMin") + condiciones.get("salMax") + condiciones.get("comm") +
						condiciones.get("deptno");

		out.println("consultaSQLCondiciones = " + consultaSQLCondiciones + " <br />");
/*** 
SENTENCIA PREPARADA		
 ****/
		out.println("<h3>Consulta parametrizada generada</h3>");
		Map <String,String> condicionesP = new HashMap<String, String>();
		condicionesP.put("select", "SELECT e.empno, e.ename, e.job, e.mgr, e.hiredate, e.sal, e.comm, e.deptno, d.dname, d.loc, e2.ename mgrEname ");
		condicionesP.put("from", "FROM EMP e, DEPT d, EMP e2 ");
		condicionesP.put("joinWhere", "WHERE e.deptno = d.deptno AND e.mgr = e2.empno ");
		// condicionesP.put("ename", "AND e.ename LIKE ? ");
		// condicionesP.put("job", "AND e.job LIKE ? ");


		String condicionEnameP = "";
		condicionesP.put("ename", "");
		if (!ename.isEmpty()) {
			condicionEnameP = "AND e.ename LIKE ? ";
			condicionesP.put("ename", "AND e.ename LIKE ? ");
		}
		
		String condicionJobP = "";
		condicionesP.put("job", "");
		if (!job.isEmpty()) {
			condicionJobP = "AND e.job LIKE ? ";
			condicionesP.put("job", "AND e.job LIKE ? ");
		}
		
		String condicionMgrP = "";
		condicionesP.put("mgr", "");
		long mgrLong = 0L;
		if (!mgr.isEmpty()) {
			condicionMgrP = "AND e.mgr = ? ";
			mgrLong = Long.parseLong(mgr);  // obtener el dato en número
			condicionesP.put("mgr", "AND e.mgr = ? ");
		}
		
		String condicionHiredateMinP = "";  // alternativa -> "AND hiredate = (SELECT MIN(hiredate) FROM EMP)";
		condicionesP.put("hiredateMin", "");
		Date hiredateMinDate = null;
		if (!hiredateMin.isEmpty()) {
			condicionHiredateMinP = "AND e.hiredate >= ? ";
			condicionesP.put("hiredateMin", "AND e.hiredate >= ? ");
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			try {
				hiredateMinDate = formatoFecha.parse(hiredateMin);
			} catch (ParseException pE) {
				pE.printStackTrace(out);
			}
		}
		
		String condicionHiredateMaxP = "";  // alternativa -> "AND hiredate <= (SELECT MAX(hiredate) FROM EMP)";
		condicionesP.put("hiredateMax", "");
		Date hiredateMaxDate = null;
		if (!hiredateMax.isEmpty()) {
			condicionHiredateMaxP = "AND e.hiredate <= ? ";
			condicionesP.put("hiredateMax", "AND e.hiredate <= ? ");
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			try {
				hiredateMaxDate = formatoFecha.parse(hiredateMax);
			} catch (ParseException pE) {
				pE.printStackTrace(out);
			}
		}
		
		String condicionSalMinP = "";
		condicionesP.put("salMin", "");
		double salMinDouble = 0D;
		if (!salMin.isEmpty()) {
			condicionSalMinP = "AND e.sal >= ? ";
			condicionesP.put("salMin", "AND e.sal >= ? ");
			salMinDouble = Double.parseDouble(salMin);
		}
		
		String condicionSalMaxP = "AND e.sal = e.sal ";
		condicionesP.put("salMax", "");
		double salMaxDouble = 0D;
		if (!salMax.isEmpty()) {
			condicionSalMaxP = "AND e.sal <= ? ";
			condicionesP.put("salMax", "AND e.sal <= ? ");
			salMaxDouble = Double.parseDouble(salMax);
		}
		
		String condicionCommP = "";
		condicionesP.put("comm", "");
		double commDouble = 0D;
		if (!comm.isEmpty()) {
			condicionCommP = "AND e.comm = ? ";
			condicionesP.put("comm", "AND e.comm = ? ");
			commDouble = Double.parseDouble(comm);
		}

		String condicionDeptnosP = "";
		condicionesP.put("deptno", "");
		if (arrayDeptnos!=null && arrayDeptnos.length > 0) {  // se recibió algún deptno
			condicionDeptnosP = "AND e.deptno IN (";
			for (String deptno : arrayDeptnos) {
				condicionDeptnosP = condicionDeptnosP + "?, ";
			}
			condicionDeptnosP = condicionDeptnosP.substring(0, condicionDeptnosP.length()-2);  // se quita la última coma (,)
			condicionDeptnosP = condicionDeptnosP + ") ";
			condicionesP.put("deptno", condicionDeptnosP);
		}
		
		/*String consultaSQLP = select + from + where + condicionEnameP + condicionJobP + condicionMgrP
						+ condicionHiredateMinP + condicionHiredateMaxP + condicionSalMinP + condicionSalMaxP
						+ condicionCommP + condicionDeptnosP;
		*/
		out.println("<div style=\"font-family: courier; font-size: 14px;\">");
		out.println(select + "<br />");
		out.println(from + " <br />");
		out.println(where + " <br />");
		out.println(condicionEnameP + " <br />");
		out.println(condicionJobP + " <br />");
		out.println(condicionMgrP + " <br />");
		out.println(condicionHiredateMinP + " <br />");
		out.println(condicionHiredateMaxP + " <br />");
		out.println(condicionSalMinP + " <br />");
		out.println(condicionSalMaxP + " <br />");
		out.println(condicionCommP + " <br />");
		out.println(condicionDeptnosP + " <br />");
		out.println("</div>");

		String consultaSQLCondicionesP = condicionesP.get("select") + condicionesP.get("from") + condicionesP.get("joinWhere") +
				condicionesP.get("ename") + condicionesP.get("job") + condicionesP.get("mgr") + 
				condicionesP.get("hiredateMin") + condicionesP.get("hiredateMax") + 
				condicionesP.get("salMin") + condicionesP.get("salMax") + condicionesP.get("comm") +
				condicionesP.get("deptno");

		out.println("consultaSQLCondicionesP = " + consultaSQLCondicionesP + " <br />");
		

		
/***
CONEXIÓN A BD 		
***/
	    // acceso a la BD
		String CADENA_CONEXION = "jdbc:mysql://localhost:3306/";
	    String NOMBRE_BD = "scott";
	    String DRIVER = "com.mysql.jdbc.Driver";
	    String USAURIO_BD = "root";
	    String CLAVE_USUARIO_BD = "root";

	    Connection conexion = null;
		PreparedStatement sentenciaPreparada = null;
		ResultSet rs = null;
	    try {
	    	Class.forName(DRIVER).newInstance();
	        conexion = (Connection) DriverManager.getConnection(CADENA_CONEXION + NOMBRE_BD, 
																			USAURIO_BD, 
																			CLAVE_USUARIO_BD);
	        sentenciaPreparada = conexion.prepareStatement(consultaSQLCondicionesP);
	        out.println("Sentencia Preparada (antes binding): " + sentenciaPreparada);
	        
	        // se va haciendo el binding dinámicamente, en función de los parámetros recibidos
	        int numeradorParametros = 1;
	        if (!condicionesP.get("ename").isEmpty()) { 
	        	sentenciaPreparada.setString(numeradorParametros, "%" + ename + "%");
	        	numeradorParametros++;
	        }
	        if (!condicionesP.get("job").isEmpty()) { 
	        	sentenciaPreparada.setString(numeradorParametros, "%" + job + "%");
	        	numeradorParametros++;
	        }
	        if (!condicionesP.get("mgr").isEmpty()) { 
	        	sentenciaPreparada.setLong(numeradorParametros, mgrLong);
	        	numeradorParametros++;
	        }
	        if (!condicionesP.get("hiredateMin").isEmpty()) {
	        	sentenciaPreparada.setDate(numeradorParametros, new java.sql.Date(hiredateMinDate.getTime()));
	        	numeradorParametros++;
	        }
	        if (!condicionesP.get("hiredateMax").isEmpty()) {
	        	sentenciaPreparada.setDate(numeradorParametros, new java.sql.Date(hiredateMaxDate.getTime()));
	        	numeradorParametros++;
	        }
	        if (!condicionesP.get("salMin").isEmpty()) {
	        	sentenciaPreparada.setDouble(numeradorParametros, salMinDouble);
	        	numeradorParametros++;
	        }
	        if (!condicionesP.get("salMax").isEmpty()) {
	        	sentenciaPreparada.setDouble(numeradorParametros, salMaxDouble);
	        	numeradorParametros++;
	        }
	        if (!condicionesP.get("comm").isEmpty()) {
	        	sentenciaPreparada.setDouble(numeradorParametros, commDouble);
	        	numeradorParametros++;
	        }
	        if (!condicionesP.get("deptno").isEmpty()) {
	        	for (String deptno : arrayDeptnos) {
					sentenciaPreparada.setLong(numeradorParametros, Long.parseLong(deptno));
					numeradorParametros++;
				}
	        }
	        

	        
	        out.println(" <hr />");
	        out.println("Sentencia Preparada (después binding): " + sentenciaPreparada);

	        rs = sentenciaPreparada.executeQuery();
	        out.println("<table border=\"1\">");
	        out.println("<tr>");
	        out.println("  <th>Empno</th>");
	        out.println("  <th>Ename</th>");
	        out.println("  <th>Job</th>");
	        out.println("  <th>Mgr</th>");
	        out.println("  <th>Hiredate</th>");
	        out.println("  <th>Sal</th>");
	        out.println("  <th>Comm</th>");
	        out.println("  <th>Deptno</th>");
	        out.println("  <th>Dname</th>");
	        out.println("</tr>");
	        while (rs.next()) {
				long empnoRS = rs.getLong("empno");
				String enameRS = rs.getString("ename");
				String jobRS = rs.getString("job");
				long mgrRS = rs.getLong("mgr");
				String mgrEnameRS = rs.getString("mgrEname");
				Date hiredateRS = rs.getDate("hiredate");
				double salRS = rs.getDouble("sal");
				double commRS = rs.getDouble("comm");
				long deptnoRS = rs.getLong("deptno");
				String dnameRS = rs.getString("dname");
				out.println("<tr>");
				out.println("  <td>" + empnoRS + "</td>");
				out.println("  <td>" + enameRS + "</td>");
				out.println("  <td>" + jobRS + "</td>");
				out.println("  <td>(" + mgrRS + ") " + mgrEnameRS + "</td>");
				out.println("  <td>" + hiredateRS + "</td>");
				out.println("  <td>" + salRS + "</td>");
				out.println("  <td>" + commRS + "</td>");
				out.println("  <td>" + deptnoRS + "</td>");
				out.println("  <td>" + dnameRS + "</td>");
				out.println("</tr>");
			}
	        out.println("</table>");
	        
		} catch (SQLException sqlE) {
			sqlE.printStackTrace(out);
		} catch (Exception e) {
			e.printStackTrace(out);
	    } finally {
			try {
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				e.printStackTrace(out);
			}
			try {
				if (sentenciaPreparada != null)
					sentenciaPreparada.close();
			} catch (SQLException e) {
				e.printStackTrace(out);
			}
			try {
				if (conexion != null)
					conexion.close();
			} catch (SQLException e) {
				e.printStackTrace(out);
			}
		}
	}

}
