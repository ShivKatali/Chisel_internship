package examples

import chisel3._
import chisel3.util._

class PWM(val n :Int) extends Module {
  val io = IO(new Bundle {
    val rise  = Input(UInt(n.W))
    val fall =  Input(UInt(n.W))
    val out = Output(UInt(n.W))
    val reset =Input(Bool())


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
  


