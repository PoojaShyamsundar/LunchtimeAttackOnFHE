public class HomomorphicMain{
    /**
     * Logic gates for NOT, XOR, AND, OR.
     */
    public long NOT(long number){
        return number^1;
    }
    public long XOR(long x, long y){
        return ( (NOT(x)*y) + (x*NOT(y)));
    }
    public long AND(long x, long y){
        return (x*y);
    }
    public long OR(long x, long y){
        return (x+y);
    }

    /**
     * method to encrypt using private key d.
     * @param bit
     * @param d
     * @return
     */
    public long[] encrypt(long bit, long d){
        long q = 2;
        long r = 0;
        long decimal=Long.parseLong(bit+"",2);
        long [] result = new long[3];
        result[0] = (q*d + 2*r + decimal);
        result[1] = q;
        result[2] = r;
        return result;
    }

    /**
     * method to decrypt using private key d.
     * @param cipher
     * @param d
     * @return
     */
    public long decrypt(long cipher, long d){
        return (cipher % d) % 2;
    }

    /**
     * Function to convert a number to bits.
     * @param number
     * @return
     */
    public static long[] convertToBits(long number) {
        long l[] = new long[8];
        l[0] = number & 0x1;
        l[1]=(number & 0x2)>>1;
        l[2]=(number & 0x4)>>2;
        l[3]=(number & 0x8)>>3;
        return l;
    }

    /**
     * Implementation of a half adder.
     * @param bit1
     * @param bit2
     * @return
     */
    public long[] halfAdderImpl(long bit1, long bit2){
        long sum = XOR(bit1,bit2);
        long carryout = AND(bit1,bit2);
        long[] res = new long[2];
        res[0] = sum;
        res[1] = carryout;
        return res;
    }

    /**
     * implementation of a full adder.
     * @param bit1
     * @param bit2
     * @param cin
     * @return
     */
    public long[] fullAdderImpl(long bit1, long bit2, long cin){
        long HARes1[] = halfAdderImpl(bit1,bit2);
        long sum1 = HARes1[0];
        long c1 = HARes1[1];
        long HARes2[] = halfAdderImpl(sum1,cin);
        long sum2 = HARes2[0];
        long c2 = HARes2[1];
        long carryout = OR(c1,c2);
        long finalRes[] = new long[2];
        finalRes[0] = sum2;
        finalRes[1] = carryout;
        return finalRes;
    }

    public static void main(String args[]){
        HomomorphicMain obj = new HomomorphicMain();
        long val1 = 10;
        long val2 = 13;
        long v1[];
        long v2[];
        v1 = convertToBits(val1);
        v2 = convertToBits(val2);
        long cin = 0;
        long d = 17;
        long[] encryptionResult = obj.encrypt(cin,d);
        long cipherCarryIn = encryptionResult[0];
        long q = encryptionResult[1];
        long r = encryptionResult[2];
        System.out.println(v1);
        System.out.println(v2);

        long enResult[] = obj.encrypt(v1[0],d);
        long cval1b0 = enResult[0];
        q = enResult[1];
        r = enResult[2];

        enResult = obj.encrypt(v2[0],d);
        long cval2b0 = enResult[0];
        q = enResult[1];
        r = enResult[2];

        enResult = obj.encrypt(v1[1],d);
        long cval1b1 = enResult[0];
        q = enResult[1];
        r = enResult[2];

        enResult = obj.encrypt(v2[1],d);
        long cval2b1 = enResult[0];
        q = enResult[1];
        r = enResult[2];

        enResult = obj.encrypt(v1[2],d);
        long cval1b2 = enResult[0];
        q = enResult[1];
        r = enResult[2];

        enResult = obj.encrypt(v2[2],d);
        long cval2b2 = enResult[0];
        q = enResult[1];
        r = enResult[2];

        enResult = obj.encrypt(v1[3],d);
        long cval1b3 = enResult[0];
        q = enResult[1];
        r = enResult[2];

        enResult = obj.encrypt(v2[3],d);
        long cval2b3 = enResult[0];
        q = enResult[1];
        r = enResult[2];

        System.out.println("1 "+cipherCarryIn);
        long FAres1[] = obj.fullAdderImpl(cval1b0,cval2b0,cipherCarryIn);
        long cipherSum1 = FAres1[0];
        long cipherCarryOut = FAres1[1];

        long FAres2[] = obj.fullAdderImpl(cval1b1,cval2b1,cipherCarryOut);
        long cipherSum2 = FAres2[0];
        cipherCarryOut = FAres2[1];

        long FAres3[] = obj.fullAdderImpl(cval1b2,cval2b2,cipherCarryOut);
        long cipherSum3 = FAres3[0];
        cipherCarryOut = FAres3[1];

        long FAres4[] = obj.fullAdderImpl(cval1b3,cval2b3,cipherCarryOut);
        long cipherSum4 = FAres4[0];
        cipherCarryOut = FAres4[1];
        long sum1 = obj.decrypt(cipherSum1,d);
        long sum2 = obj.decrypt(cipherSum2,d);
        long sum3 = obj.decrypt(cipherSum3,d);
        long sum4 = obj.decrypt(cipherSum4,d);
        long carry = obj.decrypt(cipherCarryOut,d);
        System.out.println("Val1:"+val1);
        System.out.println("Val2:"+val2);

        System.out.println("\nResults");

        System.out.println("encryption : \n");
        System.out.println("sum1 = "+(cipherSum1));
        System.out.println("sum2 = "+(cipherSum2));
        System.out.println("sum3 = "+(cipherSum3));
        System.out.println("sum4 = "+(cipherSum4));
        System.out.println("carry out = "+(carry));
        System.out.println("Sum:\t\t"+carry+sum4+sum3+sum2+sum1);

    }

}


