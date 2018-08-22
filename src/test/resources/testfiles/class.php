<?php

namespace a\b;

use x\y;
use c\d as h;

class index {
    
    private $_p = 1;
    public static $prop;


    public function __construct(){
        echo "text index contructor";
    }
    
    public static function call($c, $x = 1){
        $d = new e\f();
        $e = y::test();
        if(true){
            $a = 1;
            $b = 2;
        }
    }
}

function blub(){
    $blubber = new e\f();
}

$a = new v();

if(s::v()){
    v();
}

$a->test();