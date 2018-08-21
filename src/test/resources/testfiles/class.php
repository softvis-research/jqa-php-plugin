<?php

namespace a\b;

use x\y;
use c\d as h;

class index {
    
    private $_p = 1;


    public function __construct(){
        echo "text index contructor";
    }
    
    public function call($c){
        $d = new e\f();
        $e = y::test();
    }
}