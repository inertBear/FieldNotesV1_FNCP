/**
 * © 2017-2018 FieldNotes
 * All Rights Reserved
 * <p>
 * Created by DevHunter exclusively for FieldNotes
 */

package com.devhunter.fncp.mvc.controller.validation;

/**
 * this class will update a FieldNote that fails validation for a "stupid" reason. i.e - if the location is "turnkey",
 * but the approved location is "Turnkey". This class is allowed to make changes to the data without user knowledge ONLY when
 * it does not change the meaning of the data. only esthetic changes for consistency sake and database maintenance
 *
 */

public class FNValidationUpdater {

}
