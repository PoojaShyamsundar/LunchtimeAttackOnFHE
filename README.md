# LunchtimeAttackOnFHE
Executing a lunchtime attack on a Fully Homomorphic Encryption Scheme

L ← 0, U ← d − 1   
while U − L > 1 do   
c ← bd/(U − L)c   
b ← OD(c)   
q ← (c + b) mod 2    
k ← bLc/d + 1/2c   
B ← (k + 1/2) ∗ d/c   
if (k mod 2 = q) then   
U ← bBc   
else   
L ← dBe   
end if   
end while   
return L   



Pooja Shyamsundar pooja.shyamsundar@sjsu.edu
Homomorphic Encryption
&
Lunchtime attack on Fully Homomorphic Encryption
Scheme


ABSTRACT
A quick introduction on homomorphic
encryption in general, the Gentry-Halevi variant of the fully homomorphic encryption
scheme and the vulnerability of the Gentry-Halevi variant to lunchtime attacks is also
covered.
The attack mechanism revolves around the idea that with access to a theoretical black
box that can decrypt the encrypted data, along with some well-chosen ciphertexts, an
attacker can attempt to recover the private key and thus break the cipher. The attacker
has access to the oracle while the user is out for lunch, hence a ‘lunchtime attack’.
As a part of the attack implementation, we read the given ciphertexts and obtain the 7-
bit ascii character that they are mapped to by decrypting through querying the oracle.
By doing so, we can decipher the value of the private key by narrowing the bounds of
possibility of w which is initially from 0 to d-1 inclusive. Then w is used to decrypt the
message

INTRODUCTION
Craig Gentry presented the fully homomorphic encryption scheme. This has operations
involving ideal lattices. In this proposal, the concept of bootstrapping to take it from a
somewhat homomorphic scheme (restricted to lower degree polynomials) to a fully
homomorphic encryption scheme is introduced. A more refined and optimized version
of this encryption scheme was proposed by Gentry and Halevi, known as the GentryHalevi variant.
The CCA-1 attack is possible against the Gentry-Halevi variant of the homomorphic
encryption. This enables an attacker to retrieve the secret key in polynomial time
through access to the private-key owner’s system which can decrypt the ciphertexts.
This is basically a non-adaptive chosen ciphertext attack (CCA-1)
The private key is an integer w. The decryptions are given as ciphertext*w mod d mod 2
as the oracle decrypts any integer in the interval of 0 to d-1 inclusive to either 0 or 1. d is
provided and also the encrypted bits are given. The challenge is to execute a lunchtime
attack and decrypt this to obtain the message. The private key is obtained through
narrowing down the possibility space starting from 0 to d in order to obtain the correct
value.

ENCRYPTION SCHEME
The points below give a quick overview of the fully homomorphic encryption scheme
that is the Gentry-Halevi variant:
• The key generation method generates the public/secret key pair using a
security parameter.
• The encryption algorithm generates an output cipher text taking the message
and the public key as input.
• The decryption algorithm outputs the plaintext given the ciphertext and the
private key.
• The evaluation logic takes in the ciphertexts and the public key and executes
operations on this encrypted data.
Gentry’s scheme was derived from the somewhat homomorphic encryption scheme.
Somewhat homomorphic refers to the property of the scheme in that it allows the
evaluation of only low degree polynomials. Too many operations on the encrypted data
introduce too much noise and the end result will not be decryptable. Gentry’s scheme is
considered secure but not very practical. Gentry, van Dijk and Halevi proposed a
homomorphic encryption scheme that was based on Gentry’s idea of bootstrapping but
did not use lattices. The use of integers instead of lattices reduced the complexity of the
scheme.
For the challenge, we have picked a mechanism of homomorphic encryption that
demonstrates the concepts relating to homomorphism and the Gentry-Halevi variant but
is simpler for the purpose of implementation and execution. We use integers to
demonstrate the homomorphism.
The scheme implemented consists of the following:
● Key: We use the secret key which is an odd integer. The security parameter is
used to determine the interval that the key lies within.
● Encryption: the plaintext space here is {0,1} so we use the residue modulo to
encrypt using the formula ciphertext = privateKey*q + 2*r + m. In this formula d is
the private key and q and r, are two random integers. For the purpose of simplifying
the implementation of the scheme, we use small random values.
● Decryption - the decryption is done using ciphertext mod privateKey mod 2
● Half adder and full adder – the Boolean logic gates required to sum the binary
numbers.

IMPLEMENTATION
Input
For this challenge, the input came in a file named ‘public.txt’. This file contains the
public key d, which is a large integer. After that follow 74 cipher texts where each cipher
text consists of 7 large integers. These integers, after being operated on, will yield a
single bit. Combining these 7 bits from one cipher text, we will convert that 7-bit binary
number to an ASCII character.
Attack
This attack consists of two parts. One being online and the other being offline. The
online part requires the use of the following algorithm to get the value of private key w.
The algorithm simulates the chosen ciphertext like attack in real world scenario and
computing the private key which can be later used to decrypt any and all messages.
It can be shown that w lies in the
interval 0 to d-1 inclusive. So, at first,
we select a lower-bound (L) and an
upper-bound (U) where these are
lower and upper limits of the interval
[0, d).
Following the algorithm in the
pseudocode on the left-hand side,
this interval can be narrowed down to
a point where L = U = w.

The implementation of this part is done in JAVA using BigIntegers and BigDecimal data
types. This computation requires sending integers to the oracle and getting a decrypted
bit for them. This is the part which resembles the chosen ciphertext-like attack in the
real world. Once this value of w is retrieved, it can be later used any number of times to
do the next part of the attack.
The next part of this attack is about decrypting the cipher texts using the value of w that
was derived in the previous part. The decryption is computed by (ciphertext*w mod
secretKey) mod 2, and the modulo operation on the secret key lies between -d/2 and
d/2. Each encrypted integer is sent in this function to return a single bit. As each of the
cipher texts contains 7 such integers, we get 7 bits by sending the cipher text through a
loop of decrypting. The 7 bits are then returned in the form of a binary string. Converting
the binary number to an integer and then casting it to Character datatype, we get a
single character. Doing so with all 74 cipher texts gives us a sequence of 74 characters
which translates to the message - “You solved the challenge. Never leave your
computer unlocked during lunch!”

WORK	ANALYSIS
Fig. 1 Resource specifications of the system
Fig. 1 shows the specifications of the system that we used to execute the attack on the
previously explained fully homomorphic encryption scheme. The system runs on a 64-
bit Windows 10 operating system. Eclipse IDE was used to execute the implementation.
As explained, this attack has two parts to it. We have recorded the resource utilization
while the parts were being executed.
Part I (Online Work): Finding the secret key ‘w’
The speed of this part of attack was totally controlled by the rate of fetching of the
response from oracle. This involved two key players, first - and the major impactor -
being the limiting rate put up by mysterytwisterc3.org portal to get the response of
REST requests and second being the speed of the internet connection. Internet speed
didn’t play much role here as the size of data being exchanged between the portal and
system was around single-digit KBs.

Fig. 2.a. Size of data being exchanged between portal and system during online work
Fig. 2.b. Size of data being exchanged when system is idle
*Note: Do not look at the values in brackets, that is the total number of bytes sent from the start.
We need to look at the value after hyphen
Fig. 2 represents the difference between the bytes being sent and received when, a.
The attack was running, and oracle was being contacted on the portal to get the
operated bit and b. When the system is idle, and no attack is running. We see that
compared to the available net speed; the bandwidth consumed is negligible. Total time
taken by this part of the attack varied from 28 minutes to 95 minutes. Mathematical
operations run in the background after the value from oracle is fetched but the duration
of wait between getting the next bit from oracle is a lot as compared to processor speed.
We can safely say that the resource utilization in doing the online work in computation
of w is negligible for this attack.

Part II (Offline Work): Decrypting the ciphertexts
This part of attack is where the system’s resources actually come into picture. As the
implementation is single threaded, files are read and decrypted in a sequential manner.
We used the file containing the ciphertexts in the challenge and looped it to get the
performance numbers. So, each file contains 74 encrypted characters, and all files are
of the same size.
Fig. 3 Resource utilization during the decrypting of ciphertexts
*Note: The percentages mentioned in the above table are based on the resource values in Fig. 1
The tool used to analyze the resources is AIDA64 Extreme ver. 6.00.5100. This tool
gives a real time report of all resources in a windows system through its GUI. The
values filled in the table in Fig. 3 were monitored manually as it generates reports for
the monitoring only on an instant basis i.e. it gives a snapshot of resources at a point in
time. The analysis shows that as the number of files to be decrypted increases, we see
a reduction in time taken to decrypt a single file though it doesn’t go below an
approximate value of 77 seconds as we increase the number of files even further. The
physical memory utilization showed an increase by roughly 1% which comes to around
80 MBs. This will vary if the size of files containing ciphertexts varies. Same scenario is
seen with virtual memory utilization though the increase is a bit more here. With an
increase of about 3% i.e. 400 MBs approximately, the utilization of virtual memory
showed consistency with every run. The third percent increase was seen towards the
end of the completion of decryption. Paging file utilization showed no increase with the
test performed currently, more testing with bigger ciphertexts might change this
observation.
Talking about CPU utilization, a huge spike is seen as soon as the decryption begins.
The CPUs peak to around 90% in each case. Minimum utilization observed is 20-25%.
In some cases, the utilization dropped to 10% as well. On an average, the CPUs
oscillated between the values 30-70% while being around 40% for the majority of the
duration of run. No change was seen based on the number of files to be decrypted.

REFERENCES
[1] C. Ramaekers, "LUNCHTIME ATTACK ON THE FULLY HOMOMORPHIC
ENCRYPTION SCHEME", Mysterytwisterc3.org, 2011. [Online]. Available:
https://www.mysterytwisterc3.org/images/challenges/mtc3-ramaekers-01-fhe1-en.pdf.
[Accessed: 06- Oct- 2020].
[2] C. Gentry and S. Halevi, "Implementing Gentry's Fully-Homomorphic Encryption
Scheme", pp. 1-30, 2010. Available:
https://researcher.ibm.com/researcher/view_project.php?id=1579. [Accessed 6 October
2020].
[3] J. Loftus, A. May, N. Smart and F. Vercauteren, "On CCA-Secure Somewhat
Homomorphic Encryption", pp. 1-18, 2010. Available:
https://eprint.iacr.org/2010/560.pdf. [Accessed 6 October 2020].
[4] Van Dijk M., Gentry C., Halevi S., Vaikuntanathan V. (2010) Fully Homomorphic
Encryption over the Integers. In: Gilbert H. (eds) Advances in Cryptology –
EUROCRYPT 2010. EUROCRYPT 2010. Lecture Notes in Computer Science, vol
6110. Springer, Berlin, Heidelberg. https://doi.org/10.1007/978-3-642-13190-5_2
[5] "Crypto Journal Part III: Fully Homomorphic Encryption Part I : Secret Key
Homomorphic Encryption Over Integers", Radicalrafi.github.io, 2020. [Online]. Available:
https://radicalrafi.github.io/posts/more-homomorphic-encryption/. [Accessed: 08- Oct-
2020].
