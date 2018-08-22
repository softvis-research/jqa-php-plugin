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
    
    public function call($c){
        $d = new e\f();
        $e = y::test();
    }
}

function blub(){
    
}

$a = new v();

if(s::v()){
    v();
}

$a->test();