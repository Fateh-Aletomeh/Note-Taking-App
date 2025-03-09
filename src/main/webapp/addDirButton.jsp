<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<form action="AddDirServlet" method="post">
  <label for="newDirName">Enter Directory Name:</label>
  <input type="text" id="dirName" name="newDirName" required>
  <button type="submit">Add Directory</button>
</form>
