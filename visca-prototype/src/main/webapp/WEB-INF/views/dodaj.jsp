<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Dodaj kota</title>
	</head>
	<body>
		<a href="lista">Powrót do listy kotów</a><br />
		<form method="POST">
			<table border="1">
				<tbody>
					<tr>
						<th>Imię</th>
						<td><input type="text" name="imie" /></td>
					</tr>
					<tr>
						<th>Data urodzenia</th>
						<td><input type="text" name="dataUrodzenia" /></td>
					</tr>
					<tr>
						<th>Waga</th>
						<td><input type="text" name="waga" /></td>
					</tr>
					<tr>
						<th>Imię opiekuna</th>
						<td><input type="text" name="imieOpiekuna" /></td>
					</tr>
					<tr>
						<td colspan="2" align="right"><input type="submit" value="Dodaj!" /></td>
					</tr>
				</tbody>
			</table>
		</form>
		<br />
		<hr />
		Uwaga! W kontekście użycia HTML oraz sposobu budowania stron, te rozwiązania są bardziej antyprzykładem niż materiałem do nauki!<br />
		Jeśli chciałabyś także tworzyć same widoki i pisac poprawny, dobrze skonstruowany kod HTML, zachęcamy do zapoznania się np. z frameworkiem <a href="http://getbootstrap.com" target="_blank">Bootstrap</a>.
	</body>
</html>