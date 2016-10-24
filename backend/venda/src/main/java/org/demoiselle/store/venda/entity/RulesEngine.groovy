package org.demoiselle.jee7.entity
import org.demoiselle.jee7.entity.RegraDsl

class RulesEngine  {
    def ruleSet = []
    def debug = true
    def contRegrasAplicadas   = 0
    def contRegrasProcessadas = 0
    
    def ordena () {
         this.ruleSet.sort { -it.prioridade }
    }
    
    def fireAll() {	 
         ordena()
         this.ruleSet.each { obj -> 
            if(obj.when.call()){
           	 obj.then.call()
           	 contRegrasAplicadas++
           	 if(debug) 
           	   System.out.println("<DEBUG>:[" + contRegrasAplicadas + "] Regra:[ " + obj.nome  + " ] Prioridade:[" + obj.prioridade + "] aplicada");
           	}
           	contRegrasProcessadas++  
          }
               
          if(debug) {
          	   System.out.println("<DEBUG>: RegrasProcessadas: " + contRegrasProcessadas );
          	   System.out.println("         RegrasAplicadas  : " + contRegrasAplicadas );
          	   System.out.println("*****************************************************");          	   
          }
    } 
    
    def addRule(obj) {  
       this.ruleSet.add( RegraDsl.create(obj) )
    }   
}