<?php


?>

<style>
<?php include 'CSS/admin_portfolio.css'; ?>
<?php include 'CSS/estilos.css'; ?>
</style>


	<head>	
		<h1>Administrador de Sensores</h1>
		<br>
		<br>
		<br>
		<link rel="stylesheet" type="text/css" href="CSS/admin_portfolio.css">	
		<link rel="stylesheet" type="text/css" href="CSS/estilos.css">
	</head>
	<body>
		<script src="js/admin_portfolio.js"></script>
		<header>
			<nav class="navbar navbar-default navbar-fixed-top">	  
			</nav>
		</header>
		
		<main>

			<div class="projects_holder">
				<h1> Sensores: </h1>
				<div class="table-responsive">
					<table class="table table-condensed projects" id="projects" >
						<tr >
							<th>Nombre</th>
							<th>Valor</th>
							<th>Fecha</th>
						</tr>
						<?php
							$servername = "localhost";
							$username = "root";
							$password = "raspberry";
							$database = "alecastillo";
							$dberror1 = "Couldn't connect to out database";

							// Create connection
							$conn = new mysqli($servername, $username, $password,$database) or die ($dberror1);;

							$sql = "SELECT Sensors.SensorID, Sensors.Name, Valores.Value AS Valor, Valores.Date_Time FROM Sensors, ValuesXSensor, Valores WHERE Sensors.SensorID = ValuesXSensor.SensorID AND ValuesXSensor.ValueID = Valores.ValueID";
							$projects = $conn->query($sql);
							
							$output = "[";
							if (mysqli_num_rows($projects) > 0) {
							while($row = mysqli_fetch_assoc($projects)) {
								
								if ($output != "[") {$output .= '<tr border="1px solid black">';}
									$ProjectID = $row["ProjectID"];
									$output .= "<th>" . $row["Name"]."</th>";
								 $output .= "<th>" . $row["Valor"]."</th>";
									$output .= "<th>". $row["Date_Time"]."</th></tr>";
								}
								
							}

							echo ($output);

						?> 

					</table>
				</div>

				<br>
				<br>
				<br>
				<table id="addProject" class="addProject_visible">
					<form id="sensorForm" method="GET">
						<tr><td>Add Sensor:</td></tr>
						<tr><td> <input type="text" name="Sensor" placeholder="Valor" autofocus required>  </td></tr>
						<?php 
								if (isset($_GET["bsubmit1"])) {
									$Sensor = $_GET["Sensor"];
									include "conection.php";
									// Create connection
									$conn = new mysqli($servername, $username, $password,$database) or die ($dberror1);
									
									$sql = "INSERT INTO Sensors(Name) VALUES" . 
												"('$Sensor');";
									$conn->query($sql);
									
									
									header('Location:: current_page_url');
								}
					?>

						<tr><td>	
							<input class="btn addProjectButton" type="submit" name="bsubmit1"></input>
						</td></tr>
						<table class="submit_hidden"><tr><td>		
							<input class="submit_hidden" type="submit" id="sensorSubmit">
						</td></tr></table>
					</form>
				</table>
				
				
				<br>
				<br>
				<br>
				<table id="addProject" class="addProject_visible">
					<form id="projectForm" method="GET">
						<tr><td>Add Value:</td></tr>
						<tr><td>
							<select id="CourseAddProject" name="Course" autofocus required>
								<option value="" disabled selected><label>Sensores</label></option>
								
								<?php
									$servername = "localhost";
									$username = "root";
									$password = "raspberry";
									$database = "alecastillo";
									$dberror1 = "Couldn't connect to out database";
					
									// Create connection
									$conn = new mysqli($servername, $username, $password,$database) or die ($dberror1);;

									$sql = "SELECT Sensors.Name FROM Sensors;";
									$result = $conn->query($sql);
									
									$output = "[";
									if (mysqli_num_rows($result) > 0) {
									while($row = mysqli_fetch_assoc($result)) {
										if ($output != "[") {$output .= ",";}
											$output .= "<option name='Course' value=". $row["Name"] ."><label>" . $row["Name"]. "</label></option>";
										}
									}

									echo ($output);

								?> 

							</select>
						</td></tr>
						<tr><td> <input type="number" name="Members" placeholder="Valor" step="0.01" autofocus required>  </td></tr>
						<?php 
								if (isset($_GET["bsubmit"])) {
									$Course = $_GET['Course'];
									$Members = $_GET['Members'];
									date_default_timezone_set('America/Costa_Rica');
									$Rol =  date("Y-m-d h:i:sa");

									
									include "conection.php";
									// Create connection
									$conn = new mysqli($servername, $username, $password,$database) or die ($dberror1);
									
									$sql = "INSERT INTO Valores(Value, Date_Time) VALUES" . 
												"('$Members','$Rol');";
									$conn->query($sql);
									$ProjectID = mysqli_insert_id($conn);
									
									

									
									$sql = "INSERT INTO ValuesXSensor VALUES((SELECT SensorID FROM Sensors WHERE Name = '$Course'),$ProjectID)";
									$conn->query($sql);
									header('Location:: current_page_url');
								}
					?>
						<tr><td>	
							<input class="btn addProjectButton" type="submit" name="bsubmit"></input>
						</td></tr>
												<table class="submit_hidden"><tr><td>		
							<input class="submit_hidden" type="submit" id="projectSubmit">
						</td></tr></table>
					</form>
				</table>
				

			</div>
		</main>
		<br><br>

		<footer>
			<p>    Curso Redes 2017</p>		
		</footer>
	</body>


