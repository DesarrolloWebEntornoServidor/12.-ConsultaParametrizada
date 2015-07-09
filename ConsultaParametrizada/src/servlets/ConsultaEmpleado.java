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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebServlet("/ConsultaEmpleado")
public class ConsultaEmpleado extends HttpServlet {
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
		if (ename == null) {  
			// qué hacer si no se recibe un parámetro llamado ename
		} else {
			out.println(ename + " <br />");
			if (!ename.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		
		out.println("Job = ");
		String job = request.getParameter("job");
		if (job == null) {  
			// qué hacer si no se recibe un parámetro llamado job
		} else {
			out.println(job + " <br />");
			if (!job.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}

		String mgr = request.getParameter("mgr");
		if (mgr == null) {  
			// qué hacer si no se recibe un parámetro llamado job
		} else {
			if (!mgr.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}

		String hiredateMin = request.getParameter("hiredateMin");
		if (hiredateMin == null) {  
			// qué hacer si no se recibe un parámetro llamado job
		} else {
			if (!hiredateMin.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		
		
		String hiredateMax = request.getParameter("hiredateMax");
		if (hiredateMax == null) {  
			// qué hacer si no se recibe un parámetro llamado job
		} else {
			if (!hiredateMax.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}

		String salMin = request.getParameter("salMin");
		if (salMin == null) {  
			// qué hacer si no se recibe un parámetro llamado job
		} else {
			if (!salMin.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		
		String salMax = request.getParameter("salMax");
		if (salMax == null) {  
			// qué hacer si no se recibe un parámetro llamado job
		} else {
			if (!salMax.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		
		String comm = request.getParameter("comm");
		if (comm == null) {  
			// qué hacer si no se recibe un parámetro llamado job
		} else {
			if (!comm.isEmpty()) {
				contadorParametrosRecibidos++;
			}
		}
		
		
		out.println("Deptno = ");
		String arrayDeptnos[] = request.getParameterValues("deptno[]");
		// String deptno = request.getParameter("deptno");
		if (arrayDeptnos == null) {  
			// qué hacer si no se recibe un parámetro llamado job
		} else {
			for (String deptno : arrayDeptnos) {
				out.println(deptno + " &emsp; ");
			}
			if (arrayDeptnos.length > 0) {
				contadorParametrosRecibidos++;
			}
		}
		
		
		
		
		
		out.println("Mgr = " + mgr + " <br />");
		out.println("HiredateMin = " + hiredateMin + " &emsp; ");
		out.println("HiredateMax = " + hiredateMax + " <br />");
		out.println("SalMin = " + salMin + " &emsp; ");
		out.println("SalMax = " + salMax + " <br />");
		out.println("Comm = " + comm + " <br />");
		
		
		out.println(" <br />");
		out.println("Nº parámetros recibidos: " + contadorParametrosRecibidos + " <br />");
		
		
		out.println("<h3>Consulta generada</h3>");
		String select = "SELECT e.empno, e.ename, e.job, e.mgr, e.hiredate, e.sal, e.comm, e.deptno, d.dname, d.loc ";
		String from = "FROM EMP e, DEPT d ";
		String where = "WHERE e.deptno = d.deptno ";
		String condicionEname = "AND e.ename LIKE '%" + ename + "%' ";
		String condicionJob = "AND e.job LIKE '%" + job + "%' ";
		
		String condicionMgr = "";  // alternativa -> "AND e.mgr IS NULL"
		if (!mgr.isEmpty()) {
			condicionMgr = "AND e.mgr = " + mgr + " ";
		}
		
		String condicionHiredateMin = " ";  // alternativa -> "AND hiredate >= (SELECT MIN(hiredate) FROM EMP)";
		if (!hiredateMin.isEmpty()) {
			condicionHiredateMin = "AND e.hiredate >= '" + hiredateMin + "' ";
		}
		
		String condicionHiredateMax = " ";  // alternativa -> "AND hiredate <= (SELECT MAX(hiredate) FROM EMP)";
		if (!hiredateMax.isEmpty()) {
			condicionHiredateMax = "AND e.hiredate <= '" + hiredateMax + "' ";
		}
		
		String condicionSalMin = " ";
		if (!salMin.isEmpty()) {
			condicionSalMin = "AND e.sal >= " + salMin + " ";
		}
		
		String condicionSalMax = " ";
		if (!salMax.isEmpty()) {
			condicionSalMax = "AND e.sal <= " + salMax + " ";
		}
		
		String condicionComm = " ";
		if (!comm.isEmpty()) {
			condicionComm = "AND e.comm = " + comm + " ";
		}
		
		String condicionDeptnos = "";
		if (arrayDeptnos!=null && arrayDeptnos.length > 0) {  // se recibió algún deptno
			condicionDeptnos = "AND e.deptno IN (";
			for (String deptno : arrayDeptnos) {
				condicionDeptnos = condicionDeptnos + deptno + ", ";
			}
			condicionDeptnos = condicionDeptnos.substring(0, condicionDeptnos.length()-2);  // se quita la última coma (,)
			condicionDeptnos = condicionDeptnos + ") ";
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

/*** 
SENTENCIA PREPARADA		
 ****/
		out.println("<h3>Consulta parametrizada generada</h3>");
		//String enameP = "%" + ename + "%";
		//String jobP = "%" + ename + "%";
		String condicionEnameP = "AND e.ename LIKE ? ";
		String condicionJobP = "AND e.job LIKE ? ";
		
		String condicionMgrP = "AND e.mgr IS NULL ";
		long mgrLong = 0L;
		if (!mgr.isEmpty()) {
			condicionMgrP = "AND e.mgr = ? ";
			mgrLong = Long.parseLong(mgr);  // obtener el dato en número
		}
		
		String condicionHiredateMinP = "";  // alternativa -> "AND hiredate = (SELECT MIN(hiredate) FROM EMP)";
		Date hiredateMinDate = null;
		if (!hiredateMin.isEmpty()) {
			condicionHiredateMinP = "AND e.hiredate >= ? ";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			try {
				hiredateMinDate = formatoFecha.parse(hiredateMin);
			} catch (ParseException pE) {
				pE.printStackTrace(out);
			}
		}
		
		String condicionHiredateMaxP = "";  // alternativa -> "AND hiredate <= (SELECT MAX(hiredate) FROM EMP)";
		Date hiredateMaxDate = null;
		if (!hiredateMax.isEmpty()) {
			condicionHiredateMaxP = "AND e.hiredate <= ? ";
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
			try {
				hiredateMaxDate = formatoFecha.parse(hiredateMax);
			} catch (ParseException pE) {
				pE.printStackTrace(out);
			}
		}
		
		String condicionSalMinP = "AND e.sal = e.sal ";
		if (!salMin.isEmpty()) {
			condicionSalMinP = "AND e.sal >= ? ";
		}
		
		String condicionSalMaxP = "AND e.sal = e.sal ";
		if (!salMax.isEmpty()) {
			condicionSalMaxP = "AND e.sal <= ? ";
		}
		
		String condicionCommP = "AND e.comm = e.comm ";
		if (!comm.isEmpty()) {
			condicionCommP = "AND e.comm <= ? ";
		}

		String condicionDeptnosP = "";
		if (arrayDeptnos!=null && arrayDeptnos.length > 0) {  // se recibió algún deptno
			condicionDeptnosP = "AND e.deptno IN (";
			for (String deptno : arrayDeptnos) {
				condicionDeptnosP = condicionDeptnosP + "?, ";
			}
			condicionDeptnosP = condicionDeptnosP.substring(0, condicionDeptnosP.length()-2);  // se quita la última coma (,)
			condicionDeptnosP = condicionDeptnosP + ") ";
		}
		
		String consultaSQLP = select + from + where + condicionEnameP + condicionJobP + condicionMgrP
						+ condicionHiredateMinP + condicionHiredateMaxP + condicionSalMinP + condicionSalMaxP
						+ condicionCommP + condicionDeptnosP;
		
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
	        sentenciaPreparada = conexion.prepareStatement(consultaSQLP);
	        out.println("Sentencia Preparada (antes binding): " + sentenciaPreparada);
	        sentenciaPreparada.setString(1, "%" + ename + "%");
	        sentenciaPreparada.setString(2, "%" + job + "%");
	        sentenciaPreparada.setLong(3, mgrLong);
	        sentenciaPreparada.setDate(4, new java.sql.Date(hiredateMaxDate.getTime()));
	        out.println(" <hr />");
	        out.println("Sentencia Preparada (después binding): " + sentenciaPreparada);
/*
	        rs = sentenciaPreparada.executeQuery();
	        out.println("<table border=\"1\">");
	        out.println("<tr>");
	        out.println("  <th>Empno</th>");
	        out.println("  <th>Ename</th>");
	        out.println("  <th>Job</th>");
	        out.println("  <th>Dname</th>");
	        out.println("</tr>");
	        while (rs.next()) {
				long empnoRS = rs.getLong("empno");
				String enameRS = rs.getString("ename");
				String jobRS = rs.getString("job");
				String dnameRS = rs.getString("dname");
				out.println("<tr>");
				out.println("  <td>" + empnoRS + "</td>");
				out.println("  <td>" + enameRS + "</td>");
				out.println("  <td>" + jobRS + "</td>");
				out.println("  <td>" + dnameRS + "</td>");
				out.println("</tr>");
			}
	        out.println("</table>");
*/	        
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
