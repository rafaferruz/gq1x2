// JavaScript Document// ancho
var marqueewidth=150
// alto
var marqueeheight=150
// velocidad
var speed=1
// contenido del scroll
var marqueecontents='\
<p>\n\
<a href="http://www.elzorongo.com/Foros">FOROS de elZorongo.com</a> Un nuevo lugar para compartir el Zorongo.<br>\n\
<p>\n\
<a href="/ZORONGO/docs/comentariosresumen.jsp">TITULARES DE COMENTARIOS.</a> Una nueva opción para conocer, de un vistazo, la lista de comentarios.<br>\n\
<p>\n\
<a href="#">CONTENIDOS.</a> Nueva sección de entrevistas y reportajes. (Ver menú principal).<br>\n\
<p>\n\
<a href="/ZORONGO/docs/comentarioentrar.jsp">PARTICIPA.</a> Deja tu comentario. Lee los de tus vecinos.<br>\n\
<p>\n\
<a href="/ZORONGO/docs/anuncioslistar.jsp">TABLON DE ANUNCIOS.</a> Jardineros. Asistentas de hogar. Albañiles. Compras y Ventas.\n\
</p>'

if (document.getElementById("actualidad"))
    document.write('<marquee direction="up" scrollAmount='+speed+' style="width:'+marqueewidth+';height:'+marqueeheight+'">'+marqueecontents+'</marquee>')

function regenerate(){
    window.location.reload()
}
function regenerate2(){
    if (document.layers){
        setTimeout("window.onresize=regenerate",450)
        intializemarquee()
    }
}
function intializemarquee(){
    document.cmarquee01.document.cmarquee02.document.write(marqueecontents)
    document.cmarquee01.document.cmarquee02.document.close()
    thelength=document.cmarquee01.document.cmarquee02.document.height
    scrollit()
}
function scrollit(){
    if (document.cmarquee01.document.cmarquee02.top>=thelength*(-1)){
        document.cmarquee01.document.cmarquee02.top-=speed
        setTimeout("scrollit()",100)
    }
    else{
        document.cmarquee01.document.cmarquee02.top=marqueeheight
        scrollit()
    }
}
// window.onload=regenerate2