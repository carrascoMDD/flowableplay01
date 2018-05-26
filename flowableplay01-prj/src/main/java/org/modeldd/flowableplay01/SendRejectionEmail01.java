/*
 * org.modeldd.flowableplay01.SendRejectionEmail01.java
 *
 * Created @author Antonio Carrasco Valero 201805252222
 *
 *
 ***************************************************************************

 Copyright 2018 Antonio Carrasco Valero
 Setup a Flowable ( https://github.com/flowable/flowable-engine) playgroung on top of Spring.
 
Licensed under the EUPL, Version 1.1 only (the "Licence");
You may not use this work except in compliance with the
Licence.
You may obtain a copy of the Licence at:
https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
Unless required by applicable law or agreed to in
writing, software distributed under the Licence is
distributed on an "AS IS" basis,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
express or implied.
See the Licence for the specific language governing
permissions and limitations under the Licence.
 {{License2}}

 {{Licensed1}}
 {{Licensed2}}

 ***************************************************************************
 *
 */
package org.modeldd.flowableplay01;

import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.delegate.DelegateExecution;

public class SendRejectionEmail01 implements JavaDelegate {
	
	 public void execute(DelegateExecution execution) {
		 
		 System.out.println(
				"\n\norg.modeldd.flowableplay01.SendRejectionEmail01  Sending email to employee " +
	            execution.getVariable("employee") +
	            " number of days " + execution.getVariable("nrOfHolidays") + 
	            " reason " + execution.getVariable("description") +
	            "\n\n");
				 
	    }
}
