package teststring;


/**
 *
 * @author rajib_000
 */
public class OutputData {
    double normalMatching,characterMatching,weightedMatching;

    /**
     *
     * @param normalMatching
     * @param characterMatching
     * @param weightedMatching
     */
    public OutputData(double normalMatching, double characterMatching, double weightedMatching) {
        this.normalMatching = normalMatching;
        this.characterMatching = characterMatching;
        this.weightedMatching = weightedMatching;
    }
    
    /**
     *
     * @return
     */
    public double getNormalMatching() {
        return normalMatching;
    } 
    
    /**
     *
     * @return
     */
    public double getCharacterMatching() {
        return characterMatching;
    } 
    
    /**
     *
     * @return
     */
    public double getWeighDMatching() {
        return weightedMatching;
    } 
    
}
