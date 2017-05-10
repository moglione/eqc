<?php
/**
 * interface con el backend de wordpress
 *
 * @author Marcelo Moglione, moglione@gmail.com
 **/

// REPORTE DE ERRORES ACTIVADO
// PARA QUE NO TIRE SOLO ERROR 500
error_reporting(E_ALL);
ini_set('display_errors', 'On');


// se carga el bootstrap de wordpress y sus "helpers"
define('WP_USE_THEMES', false);
require_once('../wp-blog-header.php');


global $post;
$args = array( 'posts_per_page' => 15 ,'category_name' => "noticias-de-la-jornada" );
$lastposts = get_posts( $args );

$salida=array();
$n=0;

foreach ( $lastposts as $post ) {
    setup_postdata( $post ); 
    $titulo= get_the_title() ;
    $foto=get_the_post_thumbnail(); 
    $bajada= get_the_excerpt(); 
    $link= get_the_permalink();
    $contenido=get_the_content();
    $dia= get_the_modified_time( "d" );
    $mes= get_the_modified_time( "m" );
    
    $link=($n+1).".html";

    $meses=array("null","ENE","FEB","MAR","ABR","MAY","JUN","JUL","AGO","SEP","OCT","NOV","DIC");
    
    $i=intval($mes);
    $mes=$meses[$i];    
    
    // se limpia la bajada
    $bajada=strip_tags($bajada);
    $bajada=htmlspecialchars_decode($bajada); 

    
    // se busca la url de la foto
    $patron='#src="(.*?)"#';
    preg_match($patron, $foto, $m);
    $foto=$m[1];

    
	$salida[$n]['titulo']=$titulo;
    $salida[$n]['bajada']=$bajada;
    $salida[$n]['foto']=$foto;
    $salida[$n]['enlace']=$link;
    $salida[$n]['dia']=$dia;
    $salida[$n]['mes']=$mes;
    $salida[$n]['texto']=$contenido;

    if ($n==0) $salida[$n]['visible']="visible";
    if ($n>0)  $salida[$n]['visible']="novisible";
    
    $n++;
    

}    

echo json_encode($salida,JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE); 


wp_reset_postdata(); 

?>