

 //TOGGLE LOGIN-REGISTRO
 $(document).ready(function(){
    $(".message a").click(function(){
       $('form').animate({height: "toggle", opacity: "toggle"}, "slow");
    });
});

//HIDE LOGIN
$(document).ready(function(){
    $( "#button-ingresar" ).click(function() {
      $( '.login-page' ).hide( 1000 );
    });
});

//HIDE DIV
$(document).ready(function(){
    $(".btn-link").click(function() {
      $(this).parent().hide( 1000 );
    });
});

//SHOW NAVBAR
$(document).ready(function(){
    $( "#button-ingresar" ).click(function() {
        $('.li-navbar').first().show( 1, function showNext() {
            $(this).next('.li-navbar').show( 1, showNext );            
        });
    });
});

//SHOW CONTENT
$(document).ready(function(){
    $( "#button-ingresar" ).click(function() {
      $( '.content-page' ).show(1000);
    });
});

//SHOW PERFIL
$(document).ready(function(){
    $( "#li-perfil" ).click(function() {
      $( '.perfil-page' ).show(1000);
    });
});

//SHOW INFORMACION
$(document).ready(function(){
    $( "#li-informacion" ).click(function() {
      $( '.informacion-page' ).show(1000);
    });
});

//REFRESH PAGE
$(document).ready(function(){
    $('.navbar-brand').click(function() {
        location.reload();
    });
});

//ESTADO EN OFICINA
$(document).ready(function(){
     $(function() {
        $('#toggle-oficina').bootstrapToggle();
      });
 });

//subir noticia
var usuario="rojo"
function Subir(){
  var myFirebaseRef = new Firebase("https://redes.firebaseio.com/");
  var noticia =document.getElementById("noticia").value; 
  console.log(noticia);
  myFirebaseRef.child("noticias/"+usuario).update( {Descripcion:noticia});  
}













/*   myFirebaseRef.child("appName").on("value", function(snapshot) { 
      
        console.log(snapshot.val());
   });
   myFirebaseRef.child("appName").remove();*/
