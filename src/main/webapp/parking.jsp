<%@ page import="java.util.List" %>
<%@ page import="dao.ParkingDao" %>
<%@ page import="dao.impl.ParkingDaoImpl" %>
<%@ page import="entity.Parking" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Parking</title>
</head>
<body>
<table border="1px">
    <tr>
        <th bgcolor="#7fffd4"></th>
        <th bgcolor="#7fffd4"></th>
        <th bgcolor="#7fffd4"></th>
    </tr>
    <%
        ParkingDao parkingDao = ParkingDaoImpl.getInstance();
        List<Parking> parkings = parkingDao.getParking();
        for (Parking parking : parkings) {
    %>
    <tr>
        <td><%=parking.getName()%>
        </td>
        <td><%=parking.getParkingAreas()%>
        </td>
        <td>

        </td>

        <td>

        </td>

        <td>

        </td>
    </tr>
    <%
        }
    %>
</table>
<p>

</p>
<p>

</p>

</body>
</html>
