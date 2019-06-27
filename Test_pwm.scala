package examples

import chisel3._
import chisel3.util._

// Problem:
//
// Count incoming trues
// (increase counter every clock if 'in' is asserted)
//  

/*
 class Counter extends Module {
  val io = IO(new Bundle {
    val inc = Input(Bool())
    val amt = Input(UInt(4.W))
    val tot = Output(UInt(8.W))
  })

  //var tot :Int = 0


 def wrapAround(n: UInt, max: UInt) = 
    Mux(n > max, 0.U, n)

  def counter(max: UInt, en: Bool, amt: UInt): UInt = {
    val x = RegInit(0.U(max.getWidth.W))
    when (en) { x := wrapAround(x + amt, max) }
        x
  }

  def reset(max :UInt, en :Bool , amt :UInt )=  {
  	 val x= RegNext(0.U(max.getWidth.W))
  	 x
  }



}

*/





class Test_pwm(val n :Int) extends Module {
  val io = IO(new Bundle {
    val rise  = Input(UInt(n.W))
    val fall =  Input(UInt(n.W))
    val out = Output(UInt(n.W))
    val reset =Input(Bool())
//val tot =Output(UInt(8.W))

  })

  val risereg= RegInit(0.U(n.W))
  val fallreg= RegInit(0.U(n.W))
  val counter=RegInit(1.U(n.W))

  io.out := 0.U
 risereg:= io.rise
 fallreg := io.fall
 

  when(!io.reset){
          when(counter <= risereg)  {
          counter := counter + 1.U
          io.out := 1.U
          }
          .elsewhen(counter > risereg && counter < risereg+fallreg){
            counter := counter + 1.U
          	io.out := 0.U

          
          } .elsewhen(counter === risereg+fallreg)  {
        	counter := 1.U 
        	io.out := 0.U
            
                                             
          }
      }    
   .elsewhen(io.reset)
   {
   	  counter := 0.U
   }    

   
             

       
   


}
  


