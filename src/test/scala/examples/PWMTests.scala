package examples

import chisel3.iotesters.{ChiselFlatSpec, Driver, PeekPokeTester}

import java.io.File          
import chisel3._                                      //add these to run testerclass for verilator treadle and firrtl
import chisel3.util._
import chisel3.iotesters._
import org.scalatest.{FlatSpec, Matchers}

import treadle.TreadleTester
import org.scalatest.{Matchers, FlatSpec}

class PWMTests(c: PWM) extends PeekPokeTester(c) {

for(i <- 0 until 10){

val riseval =      rnd.nextInt((1 << (c.n-1))-2)+ 1
val fallval =      rnd.nextInt((1 << (c.n-1))-2)+1

poke(c.io.rise,riseval)
poke(c.io.fall,fallval)
poke(c.io.reset,false)
println(riseval +","+fallval)



for(i <- 0 until 2){
  for(j <- 0 until riseval){
      step(1)
      expect(c.io.out,1)
    
  }
  for(k<- 0 until fallval){                    
     step(1)
     expect(c.io.out,0)
    
  }
}

 }


}




class PWMTester extends ChiselFlatSpec {
  behavior of "PWM"
  backends foreach {backend =>
    it should s"correctly accumulate randomly generated numbers in $backend" in {                      //class for tester
      Driver(() => new PWM(8), backend)(c => new PWMTests(c)) should be (true)
    }
  }
}


class PWM_verilatorTester extends ChiselFlatSpec{
   behavior of "PWM"
   
   it should "run verilator via command line arguments" in {                           // class for verilator
    // val args = Array.empty[String]
    val args = Array("--backend-name", "verilator")
    iotesters.Driver.execute(args, () => new PWM(8)) { c =>
      new PWMTests(c)
    } should be (true)
  }
}


class PWM_firrtlTester extends ChiselFlatSpec{
   behavior of "PWM"

 it should "run firrtl via command line arguments" in {
    // val args = Array.empty[String]                                                     //classfor FIRTEL     
    val args = Array("--backend-name", "firrtl", "--fint-write-vcd")
    iotesters.Driver.execute(args, () => PWM(8)) { c =>
      new PWMTests(c)
    } should be (true)
  }
}


class PWM_treadleTester extends ChiselFlatSpec{
   behavior of "PWM"

 it should "run treadle via command line arguments" in {
    // val args = Array.empty[String]                                                     //classfor TREADLE
    val args = Array("--backend-name", "treadle", "-tiwv")
    iotesters.Driver.execute(args, () => new PWM(8)) { c =>
      new PWMTests(c)
    } should be (true)
  }
}



