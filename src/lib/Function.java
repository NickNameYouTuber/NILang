/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lib;

import lib.Value;

/**
 *
 * @author NNI
 */
public interface Function {
    Value execute(Value... args);
}
