// JavaScripts comunes a todos los navegadores
// El siguiente script muestra las fotografï¿½as de 50 pixels del aï¿½o 1967 en la cabecera de las pï¿½ginas
// Created by Pierre Volpe

var div_inicial ; // destinado a definir en él el nombre del elemento 'div' que se debe mostrar al cargar la página
//
//
// Duration of image (in milliseconds)
var slideShowSpeed = 3000

// Duration of crossfade (in seconds)
var crossFadeDuration = 1

var Pic = new Array() 

Pic[0] = 'http://www.ecosysw.com/ZORONGO/WEB-INF/imagenes/fotos/ff03.jpg'


var t
var j = 0
var p = Pic.length
j = Math.floor( p * Math.random() )
var preLoad = new Array()

for (i = 0; i < p; i++)
{
    preLoad[i] = new Image()
    preLoad[i].src = Pic[i]
}

function runSlideShow(){
    if (document.all && document.images.SlideShow.filters){
        document.images.SlideShow.style.filter="blendTrans(duration=2)"
        document.images.SlideShow.style.filter="blendTrans(duration=crossFadeDuration)"
        document.images.SlideShow.filters.blendTrans.Apply()      
    }
    document.images.SlideShow.src = preLoad[j].src
    if (document.all && document.images.SlideShow.filters){
        document.images.SlideShow.filters.blendTrans.Play()
    }
    //   j = j + 1
    //   if (j > (p-1)) j=0
    j = Math.floor( p * Math.random() )
    t = setTimeout('runSlideShow()', slideShowSpeed)
}

function MM_callJS(jsStr) { //v2.0
    return eval(jsStr)
}
//-->


//El siguiente script abre la ventana de galerï¿½a de fotos que corresponda segï¿½n la selecciï¿½n realizada en una lista
function destino(){
    url = document.fotos_encuentro_2008.galeria.options[document.fotos_encuentro_2008.galeria.selectedIndex].value
    //document.fotos_encuentro_2008.texto.value=url
    if (url != "no") mostrarVentana(url,"galeria_fotos","800","600","0","resizable=yes");
}


//El siguiente script abre una nueva ventana con diferentes tamaï¿½os y opciones segï¿½n  los parï¿½metros que se le pasan
function mostrarVentana(URL,nombre,ancho,alto,barraMenu,otrasOpciones)
{

    var opciones = "" 
    if (isNaN(ancho)){
	opciones = opciones + "width=400"
    }
    else{
	opciones = opciones + "width=" + ancho
    }
    if (isNaN(alto)){
	opciones = opciones + ",height=300"
    }
    else{
	opciones = opciones + ",height=" + alto
    }
    if (isNaN(barraMenu)){
	opciones = opciones + ",menubar=no"
    }
    else
    {
	if (barraMenu==0) {
            opciones = opciones + ",menubar=no"
        } else	{
            opciones = opciones + ",menubar=yes"
        }
    }
    if (otrasOpciones.length > 0) {
	opciones = opciones + "," + otrasOpciones
    }
    window.open(URL,nombre,opciones)
    //window.focus(nombre)
}



// Saca un mensaje con scroll horizontal en la barra de estado
//texto del mensaje
var texto_estado = "             La gente se arregla todos los días el cabello. ¿Por qué no el corazón?            Si te caes siete veces, levántate ocho.                  Las grandes almas tienen voluntades; las débiles tan solo deseos.             El sabio puede sentarse en un hormiguero, pero sólo el necio se queda sentado en él.                Cuando te inunde una enorme alegría, no prometas nada a nadie. Cuando te domine un gran enojo, no contestes ninguna carta.              Si quieres que algo se haga, encárgaselo a una persona ocupada.            Nunca se pierden los años que se quita una mujer, van a parar a cualquiera de sus amigas.          El que teme sufrir ya sufre el temor.        El momento elegido por el azar vale siempre más que el momento elegido por nosotros mismos.          Es más fácil variar el curso de un río que el carácter de un hombre."            
    
var posicion = 0
//funcion para mover el texto de la barra de estado
function mueve_texto(){
    if (posicion < texto_estado.length)
        posicion ++;
    else
        posicion = 1;
    string_actual = texto_estado.substring(posicion) + texto_estado.substring(0,posicion)
    window.status = string_actual
    setTimeout("mueve_texto()",200)
}
//	mueve_texto()

//-------------------------------------------------------------------
// Funciones utilizadas en el módulo comentariosseleccionar



var calendario=null;
var calendarioVisible=false;
var tDate=new Date();
var iCurrentMonth=tDate.getMonth(); 
var iCurrentYear=tDate.getYear(); 

function PreviousMonth(){
    ClearCalendar();
    --iCurrentMonth;
    if (iCurrentMonth<0) {
        iCurrentMonth=11;
        --iCurrentYear;
    }
    SetCalendar(1,iCurrentMonth,iCurrentYear);
}

function NextMonth(){
    ClearCalendar();
    ++iCurrentMonth;
    if (iCurrentMonth==12) {
        iCurrentMonth=0;
        ++iCurrentYear;
    }
    SetCalendar(1,iCurrentMonth,iCurrentYear);
}

function ClearCalendar(){
    var i=0;
    var j=0; 
    var oCurRow;
    var oCell;
    var oTable=document.all("tblCalendar");

    for (i=2;i<8;i++){
        oCurRow = oTable.rows[i];
        for (j=0;j<7;j++){
            oCell=oCurRow.cells[j];
            oCell.innerHTML = "<font face='Verdana, Arial, Helvetica, sans-serif' size='1'>&nbsp;</font>";
        }
    }
}

function GetDaysPerMonth(iMonth, iYear){
    switch (iMonth){
        case 0: return 31; break;
        case 1: 
            if (iYear % 4 == 0){
                if (iYear % 400 == 0){
                    return 29;
                }
                else {
                    if (iYear % 100 == 0){
                        return 28;
                    }
                    else {
                        return 29;
                    }
                }
            }
            else {
                return 28; 
            }; 
            break;
        case 2: return 31; break;
        case 3: return 30; break;
        case 4: return 31; break;
        case 5: return 30; break;
        case 6: return 31; break;
        case 7: return 31; break;
        case 8: return 30; break;
        case 9: return 31; break;
        case 10: return 30; break;
        case 11: return 31; break;

    }
}

function SetCalendar(dia,mes,anio){
    var meses = new Array(12);
    meses[0]="ENE";
    meses[1]="FEB";
    meses[2]="MAR";
    meses[3]="ABR";
    meses[4]="MAY";
    meses[5]="JUN";
    meses[6]="JUL";
    meses[7]="AGO";
    meses[8]="SEP";
    meses[9]="OCT";
    meses[10]="NOV";
    meses[11]="DIC";
    var iDay=0;

    ClearCalendar();

    iCurrentYear=anio;
    iCurrentMonth=mes;
    var tFirstDayDate = new Date(iCurrentYear,iCurrentMonth,1);
    var iLastDayMonth=GetDaysPerMonth(iCurrentMonth, iCurrentYear);
    var iCol=tFirstDayDate.getDay(); //0-Diumenge ... 6-Dissabte 
    --iCol;
    if (iCol <0) {
        iCol = 6;
    }
    //alert(iCol + "-" + tFirstDayDate);
    var iRow=2;
    var oTable=document.all("tblCalendar");
    //	alert(oTable.rows.length + '-'+ iCol);
    var oCurRow;
    var oCell;

    for (iDay=1;iDay<=iLastDayMonth;iDay++){
        oCurRow = oTable.rows[iRow];
        //alert(oCurRow.cells.length);
        oCell=oCurRow.cells[iCol];
        if (iDay!=dia) {
            oCell.innerHTML = "<div id='div" + iDay + "' align='center' onclick='SetFecha(" + iDay + "," + iCurrentMonth + "," + iCurrentYear + ")' onmouseover='Set_back_gris(this)' onmouseout='Set_back_blanco(this)')><font face='Verdana, Arial, Helvetica, sans-serif' size='1'>"+iDay+"</font></div>";
        } else {
            oCell.innerHTML = "<div id='div" + iDay + "' align='center' class='div_ColorDiaCalendario' onclick='SetFecha(" + iDay + "," + iCurrentMonth + "," + iCurrentYear + ")' onmouseover='Set_back_gris(this)' onmouseout='Set_back_blanco(this)')>"+iDay+"</div>";
        }
        iCol++;
        if (iCol>6) {
            iCol=0;
            iRow++;
        }
    }
    oCurRow= oTable.rows[0];
    oCell=oCurRow.cells[1];
    oCell.innerHTML = "<div align='center'><font face='Verdana, Arial, Helvetica, sans-serif' size='1'>"+(meses[iCurrentMonth])+" - "+iCurrentYear+"</font></div>";

}

function SetFecha(dia,mes,anio) {
    if (calendario=='desde') { 
        document.formFechas.diaDesde.options[dia-1].selected=true;
        document.formFechas.mesDesde.options[mes].selected=true;
        document.formFechas.anioDesde.options[anio-2008].selected=true;
    }
    if (calendario=='hasta') { 
        document.formFechas.diaHasta.options[dia-1].selected=true;
        document.formFechas.mesHasta.options[mes].selected=true;
        document.formFechas.anioHasta.options[anio-2008].selected=true;
    }
    document.all("divCalendario").style.visibility="hidden";
    calendarioVisible=false;
}

function Set_back_gris(objeto) {
    objeto.style.backgroundColor="#999999";
}
function Set_back_blanco(objeto) {
    objeto.style.backgroundColor="#66FF66";
}
function mostrarCalendario(event){
    if (calendario=='desde') {
        SetCalendar( document.formFechas.diaDesde.selectedIndex + 1,
        document.formFechas.mesDesde.selectedIndex,
        document.formFechas.anioDesde.options[document.formFechas.anioDesde.selectedIndex].value)

    }
    if (calendario=='hasta') {
        SetCalendar( document.formFechas.diaHasta.selectedIndex + 1,
        document.formFechas.mesHasta.selectedIndex,
        document.formFechas.anioHasta.options[document.formFechas.anioHasta.selectedIndex].value)

    }

    if (calendarioVisible==false) {
        x=event.clientX;
        y=event.clientY;
        document.all("divCalendario").style.left = x-60;
        document.all("divCalendario").style.top = y+10;

        document.all("divCalendario").style.visibility="visible";
        calendarioVisible=true;
    } else {
        document.all("divCalendario").style.visibility="hidden";
        calendarioVisible=false;
    }
}

function validarFormularioNoticiasSeleccionar(){
    //extraemos el valor del campo
    textoCampo = window.document.formFechas.maxreg.value
    //lo validamos como entero
    textoCampo = validarEntero(textoCampo)
    //colocamos el valor de nuevo
    if (textoCampo == "") {
        alert("El campo 'Nº max. de Noticias' debe ser numérico");
        return false;
    }
    if (textoCampo < 1) {
        alert("El campo 'Nº max. de Noticias' debe ser mayor que 0");
        return false;
    }

    // Comprobamos palabras a buscar
    textoCampo = window.document.formFechas.buscartexto.value;
    if (textoCampo.length>0) {
        textoCampo=validarAlfanumerico(textoCampo);
        if (textoCampo == "") {
            alert("El campo 'Palabras a buscar' contiene caracteres no alfanuméricos");
            return false;
        }  
    }
	  
    if (validarFechas()==true) {
        return true; 
    }
	return false;  
} 

function validarFormularioPaginasSeleccionar(){
    //extraemos el valor del campo
    textoCampo = window.document.formFechas.maxreg.value
    //lo validamos como entero
    textoCampo = validarEntero(textoCampo)
    //colocamos el valor de nuevo
    if (textoCampo == "") {
        alert("El campo 'Nº max. de Páginas' debe ser numérico");
        return false;
    }
    if (textoCampo < 1) {
        alert("El campo 'Nº max. de Páginas' debe ser mayor que 0");
        return false;
    }

    // Comprobamos palabras a buscar
    textoCampo = window.document.formFechas.buscartexto.value;
    if (textoCampo.length>0) {
        textoCampo=validarAlfanumerico(textoCampo);
        if (textoCampo == "") {
            alert("El campo 'Palabras a buscar' contiene caracteres no alfanuméricos");
            return false;
        }  
    }
	  
    if (validarFechas()==true) {
        return true; 
    }
	return false;  
} 

function validarFormularioComentariosSeleccionar(){
    //extraemos el valor del campo
    textoCampo = window.document.formFechas.maxreg.value
    //lo validamos como entero
    textoCampo = validarEntero(textoCampo)
    //colocamos el valor de nuevo
    if (textoCampo == "") {
        alert("El campo 'Nº max. de comentarios' debe ser numérico");
        return false;
    }
    if (textoCampo < 1) {
        alert("El campo 'Nº max. de comentarios' debe ser mayor que 0");
        return false;
    }

    // Comprobamos palabras a buscar
    textoCampo = window.document.formFechas.buscartexto.value;
    if (textoCampo.length>0) {
        textoCampo=validarAlfanumerico(textoCampo);
        if (textoCampo == "") {
            alert("El campo 'Palabras a buscar' contiene caracteres no alfanuméricos");
            return false;
        }  
    }
	  
    if (validarFechas()==true) {
        window.document.formFechas.submit(); 
    }
	return false;  
} 

function validarFormularioAnunciosSeleccionar(){
    //extraemos el valor del campo
    textoCampo = window.document.formFechas.maxreg.value
    //lo validamos como entero
    textoCampo = validarEntero(textoCampo)
    //colocamos el valor de nuevo
    if (textoCampo == "") {
        alert("El campo 'Nº max. de anuncios' debe ser numérico");
        return false;
    }
    if (textoCampo < 1) {
        alert("El campo 'Nº max. de anuncios' debe ser mayor que 0");
        return false;
    }

    // Comprobamos palabras a buscar
    textoCampo = window.document.formFechas.buscartexto.value;
    if (textoCampo.length>0) {
        textoCampo=validarAlfanumerico(textoCampo);
        if (textoCampo == "") {
            alert("El campo 'Palabras a buscar' contiene caracteres no alfanuméricos");
            return false;
        }  
    }
	  
    if (validarFechas()==true) {
        window.document.formFechas.submit(); 
    }
	return false;  
} 

function validarEntero(valor){
    //intento convertir a entero.
    //si era un entero no le afecta, si no lo era lo intenta convertir
    valor = parseInt(valor)

    //Compruebo si es un valor numérico
    if (isNaN(valor)) {
        //entonces (no es numero) devuelvo el valor cadena vacia
        return "";
    }else{
        //En caso contrario (Si era un número) devuelvo el valor
        return valor;
    }
} 

function validarAlfanumerico (texto) {
    var caracteres_validos=" 0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz";
    for(i=0; i<texto.length; i++){
        if (caracteres_validos.indexOf(texto.charAt(i),0)==-1){
            return "";
        }
    }
    return texto;
} 

function validarFechas() {
    if (window.document.formFechas.diaDesde.options[window.document.formFechas.diaDesde.selectedIndex].value > GetDaysPerMonth(window.document.formFechas.mesDesde.selectedIndex , window.document.formFechas.anioDesde.options[window.document.formFechas.anioDesde.selectedIndex].value)) {
        alert("Fecha 'Desde' incorrecta.");
        return false;
    }
    if (window.document.formFechas.diaHasta.options[window.document.formFechas.diaHasta.selectedIndex].value > GetDaysPerMonth(window.document.formFechas.mesHasta.selectedIndex , window.document.formFechas.anioHasta.options[window.document.formFechas.anioHasta.selectedIndex].value)) {
        alert("Fecha 'Hasta' incorrecta.");
        return false;
    }

    mifechaDesde = new Date(window.document.formFechas.anioDesde.options[window.document.formFechas.anioDesde.selectedIndex].value , window.document.formFechas.mesDesde.selectedIndex + 1 , window.document.formFechas.diaDesde.options[window.document.formFechas.diaDesde.selectedIndex].value)

    mifechaHasta = new Date(window.document.formFechas.anioHasta.options[window.document.formFechas.anioHasta.selectedIndex].value , window.document.formFechas.mesHasta.selectedIndex + 1 , window.document.formFechas.diaHasta.options[window.document.formFechas.diaHasta.selectedIndex].value)

    if (mifechaDesde > mifechaHasta) {
        alert("Fecha 'Desde' es posterior a Fecha 'Hasta'.");
        return false;
    }

    window.document.formFechas.fechaDesde.value = window.document.formFechas.anioDesde.options[window.document.formFechas.anioDesde.selectedIndex].value + "/" + (window.document.formFechas.mesDesde.selectedIndex + 1) + "/" + window.document.formFechas.diaDesde.options[window.document.formFechas.diaDesde.selectedIndex].value

    window.document.formFechas.fechaHasta.value = window.document.formFechas.anioHasta.options[window.document.formFechas.anioHasta.selectedIndex].value + "/" + (window.document.formFechas.mesHasta.selectedIndex + 1) + "/" + window.document.formFechas.diaHasta.options[window.document.formFechas.diaHasta.selectedIndex].value

    return true;
}


// Comprueba que una fecha es válida
//<script language = "Javascript">
/**
 * DHTML date validation script for dd/mm/yyyy. Courtesy of SmartWebby.com (http://www.smartwebby.com/dhtml/)
 */
// Declaring valid date character, minimum year and maximum year
var dtCh= "/";
var minYear=1900;
var maxYear=2100;

function isInteger(s){
	var i;
    for (i = 0; i < s.length; i++){   
        // Check that current character is number.
        var c = s.charAt(i);
        if (((c < "0") || (c > "9"))) return false;
    }
    // All characters are numbers.
    return true;
}

function stripCharsInBag(s, bag){
	var i;
    var returnString = "";
    // Search through string's characters one by one.
    // If character is not in bag, append to returnString.
    for (i = 0; i < s.length; i++){   
        var c = s.charAt(i);
        if (bag.indexOf(c) == -1) returnString += c;
    }
    return returnString;
}

function daysInFebruary (year){
	// February has 29 days in any year evenly divisible by four,
    // EXCEPT for centurial years which are not also divisible by 400.
    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
}
function DaysArray(n) {
	for (var i = 1; i <= n; i++) {
		this[i] = 31
		if (i==4 || i==6 || i==9 || i==11) {this[i] = 30}
		if (i==2) {this[i] = 29}
   } 
   return this
}

function isDate(dtStr){
	var daysInMonth = DaysArray(12)
	var pos1=dtStr.indexOf(dtCh)
	var pos2=dtStr.indexOf(dtCh,pos1+1)
	var strDay=dtStr.substring(0,pos1)
	var strMonth=dtStr.substring(pos1+1,pos2)
	var strYear=dtStr.substring(pos2+1)
	strYr=strYear
	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1)
	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1)
	for (var i = 1; i <= 3; i++) {
		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1)
	}
	month=parseInt(strMonth)
	day=parseInt(strDay)
	year=parseInt(strYr)
	if (pos1==-1 || pos2==-1){
		alert("El formato del campo 'Fecha' debería ser : dd/mm/yyyy")
		return false
	}
	if (strMonth.length<1 || month<1 || month>12){
		alert("Por favor, entre un mes válido")
		return false
	}
	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		alert("Por favor, entre un día válido")
		return false
	}
	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		alert("Por favor, entre un mes año de 4 dígitos válido entre "+minYear+" y "+maxYear)
		return false
	}
	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		alert("Por favor, entre una fecha válida")
		return false
	}
return true
}

function ValidarFecha(objeto){
	if (isDate(objeto.value)==false){
		objeto.focus()
		return false
	}
    return true
 }


        boton = new Array();
        boton[0] = "solapa1on";
        boton[1] = "solapa2on";
//        boton[2] = "solapa3on";

        contenido = new Array();
        contenido[0]= "solapa1info";
        contenido[1]= "solapa2info";
//        contenido[2]= "solapa3info";

        function mostrar(tab) {
            for (var i = 0; i < boton.length; i++) {
                if (boton[i] == tab) {
                    visualizar(boton[i]);
                    visualizar(contenido[i]);
                } else {
                    ocultar(boton[i]);
                    ocultar(contenido[i]);
                }
            }
        }

        browser = navigator.appName;
        netscape = "Netscape";
        netver=parseFloat(navigator.appVersion);
    
        function visualizar(panel) {
            if (browser == netscape) {
                if (netver >= 5) {
                    document.getElementById(panel).style.visibility = 'visible';
                } else {
                    document.layers[panel].visibility = 'visible'; // NS4
                } 
            } else {
                document.all[panel].style.visibility = 'visible';  // IE y Opera
            }
        }
        function ocultar(panel) {
            if (browser == netscape) {
                if (netver >= 5) {
                    document.getElementById(panel).style.visibility = 'hidden';
                } else {
                    document.layers[panel].visibility = 'hidden'; // NS4
                } 
            } else {
                document.all[panel].style.visibility = 'hidden';  // IE y Opera
            }
        }

