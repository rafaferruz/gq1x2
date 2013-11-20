/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function clickCol(numCol){
    var check;
    for ( var i = 0; i < 14; i++) {
        if (i==0){
            check=!document.forms['form1']['form1:dataTableBetLines:'+i+':checkbox20'+numCol].checked 
        }
        document.forms['form1']['form1:dataTableBetLines:'+i+':checkbox20'+numCol].checked=check;
    }
}
