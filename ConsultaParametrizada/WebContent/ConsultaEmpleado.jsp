<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<html>
<body>
  <h1>Consulta parametrizada de empleados</h1>
  <form name="fConsulta" id="fConsulta" action="ConsultaEmpleado" method="post">
    <label for="empno">Empno</label> <input type="text" name="empno" id="empno" disabled="disabled" /> <br />
    <label for="ename">Ename</label> <input type="text" name="ename" id="ename" value="rs" /> <br />
    <label for="job">Job</label> <input type="text" name="job" id="job" value="es"/> <br />
    <label for="mgr">Mgr</label> <input type="text" name="mgr" id="mgr" value="1357" /> <br />
    <label for="hiredateMin">Hiredate min</label> <input type="text" name="hiredateMin" id="hiredateMin" />
    <label for="hiredateMin">Hiredate max</label> <input type="text" name="hiredateMax" id="hiredateMax" value="2010-10-05" /> <br />
    <label for="salMin">Sal min</label> <input type="text" name="salMin" id="salMin" />
    <label for="salMax">Sal max</label> <input type="text" name="salMax" id="salMax" value="3000.00" /> <br />
    <label for="comm">Comm</label> <input type="text" name="comm" id="comm" /> <br />
    <label for="deptno">Deptno</label> 
    <select name="deptno[]" multiple="multiple">
    	<option value="10">ACCOUNTING</option>
    	<option value="20">RESEARCH</option>
    	<option value="30">SALES</option>
    	<option value="40">OPERATIONS</option>
    </select>
    <input type="button" value="Consulta" id="enviarConsulta" 
    	onclick = "this.form.action='ConsultaEmpleado'; this.form.submit();" />
    <input type="button" value="Consulta Parametrizada" id="enviarConsultaParametrizada" 
    	onclick = "this.form.action='ConsultaParametrizadaEmpleado'; this.form.submit();" />
  </form>
</body>
</html>