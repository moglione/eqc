<?php
///////////////////////////////////////
//　Marcelo Moglione 2016 
////////////////////////////////////////



//$proxy = '10.100.13.220:3128';
$proxy ="";



// Make sure this script will keep on runing after we close the connection with it.
ignore_user_abort(TRUE);
ini_set('html_errors', false);
set_time_limit ( 0 );


// REPORTE DE ERRORES ACTIVADO
// PARA QUE NO TIRE SOLO ERROR 500
error_reporting(E_ALL);
ini_set('display_errors', 'On');


function consolelog( $message, $progress=0) 
{ 
    echo $message ."<br>" .PHP_EOL; 
    echo PHP_EOL; ob_flush();  
    flush();  
}



// FUNCION CURL PARA BAJAR LAS PAGINAS
function getPage($url, $proxy) {
    
    $agent= 'Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.0.3705; .NET CLR 1.1.4322)';


    $ch = curl_init();
    
    if($proxy!=="") curl_setopt($ch, CURLOPT_PROXY, $proxy);
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_USERAGENT, $agent);
    
    curl_setopt($ch,CURLOPT_SSL_VERIFYPEER, false);
    // por las dudas la respuestas sean  content-encoding:gzip
    curl_setopt($ch,CURLOPT_ENCODING , "gzip");
    $html =  curl_exec($ch);
    if($html === false)  echo 'Curl error: ' . curl_error($ch);
    return $html;
}



echo '<meta charset="UTF-8">';


$url = "http://elquiddelacuestion.com.ar/app/tapa.php";


$html=getPage($url,$proxy);


// se quitan los doble espacios o "trenes" de espacios
$html=preg_replace('/\s+/', ' ', $html);  



$anterior=file_get_contents("salida/anterior.html");

if ($anterior!=$html){
    // hay noyicias nuevas
    $notificar=time()."/*/".PHP_EOL;
    $notificar.="Hay noticias nuevas para leer.".PHP_EOL;
    file_put_contents("salida/notificaciones.txt", $notificar);
       
}

file_put_contents("salida/anterior.html", $html);


$entradas=json_decode($html,true);


include_once('includes/tbs_class.php');
$TBS = new clsTinyButStrong;


$TBS->LoadTemplate('templates/tapa.html');
$TBS->MergeBlock('bloque1',$entradas); 
$TBS->Show(TBS_NOTHING); // terminate the merging without leaving the script nor to display the result
$html = $TBS->Source;


$htmlpath="salida/elquid.html";
file_put_contents($htmlpath, $html);

/////////////////////////////////
// cada una de las notas
////////////////////////////////

foreach ($entradas as $key => $value) {
    
    $titulo= $value['titulo'];
    $texto= $value['texto'];
    $foto=$value['foto'];
    $mes=$value['mes'];
    $dia=$value['dia'];


    $textoleido=$texto;

    $texto=nl2br($texto);
    
    $textoleido=$titulo.PHP_EOL.strip_tags($textoleido);
    $textoleido=str_replace(array('<br/>','<br />', '&amp;', 'nbsp;','&', PHP_EOL), ' ', $textoleido);

    //$textoleido=str_replace(array('.'), '.'.PHP_EOL, $textoleido);

    $textoleido.=". Gracias por escuchar esta nota leida de El quid de la cuestión. Hasta luego.  ";

  

    ////////////////////////////////////////////////
    //se graba el archivo html de cada noticia
    $TBS = new clsTinyButStrong;
    //$TBS->SetOption('charset', false);
    $TBS->LoadTemplate('templates/nota.html');                           
    $TBS->Show(TBS_NOTHING); 
    $html = $TBS->Source;
    $htmlpath="salida";
    if(!file_exists($htmlpath)) mkdir($htmlpath); 
    $htmlpath=$htmlpath."/".$value['enlace'];
    file_put_contents($htmlpath, $html);   


}


echo "<h2>listo EQC (app movil) fue actualizado</h2>";
?>