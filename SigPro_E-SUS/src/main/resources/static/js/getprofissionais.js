
GET: $(document).ready(
		function() {

			// GET REQUEST
			$("#getALlProfissionais").click(function(event) {
				var equipe = $('#equipe').val();
				event.preventDefault();
				ajaxGet(equipe);
			});

			// DO GET
			function ajaxGet(equipe) {
				$.ajax({
					type : "GET",
					url : "getProfissionais/equipe",
					success : function(result) {
						if (result.status == "success") {
							$('#profissionais ul').empty();
							var custList = "";
							$.each(result.data,
									function(i, profissional) {
										var user = "Profisional  "
												+ profissional.nome
												+ ", Equipe  = " + profissional.equipe
												+ "<br>";
										$('#profissional .list-group').append(
												user)
									});
							console.log("Success: ", result);
						} else {
							$("#profissionais").html("<strong>Error</strong>");
							console.log("Fail: ", result);
						}
					},
					error : function(e) {
						$("#profissionais").html("<strong>Error</strong>");
						console.log("ERROR: ", e);
					}
				});
			}
})