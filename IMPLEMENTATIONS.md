# Implementation

This document will provide a list of various primitives and links to their implementations. In many cases, you will find that the implementation does not exist. Please feel free to contribute to the project by submitting a pull request with your implementation.

### Bit I/O Library

| Feature                      | Implemented | Filename                                              | Example (if not implemented) |
|----------------------------:|:-----------:|------------------------------------------------------------|:-----------------------------|
| BitInputStream (read bits)   | ✅          | [BitInputStream](src/main/java/org/dcp/io/BitInputStream.java)               | X                            |
| BitOutputStream (write bits) | ✅          | [BitOutputStream](src/main/java/org/dcp/io/BitOutputStream.java)              | X                            |

### Bits

| Feature                  | Implemented | Filename                                                    | Example (if not implemented) |
|------------------------:|:-----------:|------------------------------------------------------------------|:-----------------------------|
| Bit                      | ✅          | [Bit](src/main/java/org/dcp/entities/bit/Bit.java)                      | X                            |
| BitBuffer                | ✅          | [BitBuffer](src/main/java/org/dcp/entities/bit/BitBuffer.java)                | X                            |
| BitStreamSerializable    | ✅          | [BitStreamSerializable](src/main/java/org/dcp/entities/bit/BitStreamSerializable.java)    | X                            |

### Primitives & Variable Length Code Primitives

| Primitive            | Implemented | Filename                                                        | Example (if not implemented) |
|--------------------:|:-----------:|---------------------------------------------------------------------|:-----------------------------|
| Integer              | ✅          | [Integer](src/main/java/org/dcp/entities/primitives/Integer.java)               | X                            |
| Unsigned Integer     | ✅          | [UnsignedInteger](src/main/java/org/dcp/entities/primitives/UnsignedInteger.java)       | X                            |
| Decimal              | ✅          | [Decimal](src/main/java/org/dcp/entities/primitives/Decimal.java)               | X                            |
| VarUnsignedInteger   | ✅          | [VarUnsignedInteger](src/main/java/org/dcp/entities/primitives/VarUnsignedInteger.java)    | X                            |
| UnaryInteger         | ✅          | [UnaryInteger](src/main/java/org/dcp/entities/primitives/UnaryInteger.java)          | X                            |
| Elias Coded Integer (Gamma, Delta, Omega) | ❌ | X | [Example](https://github.com/naoya/perl-integer-elias), [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/Omega.pm) |
| Even Rodeh Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/EvenRodeh.pm) |
| Taboo Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/Taboo.pm) |
| Levenstein Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/Levenstein.pm) |
| Comma Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/Comma.pm) |
| Escape Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/Escape.pm) |
| Golomb Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/Golomb.pm) |
| Gamma Golomb Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/GammaGolomb.pm) |
| Exponential Golomb Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/ExponentialGolomb.pm) |
| Rice Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/Rice.pm) |
| Adaptive Rice Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/ARice.pm) |
| Fibonacci Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/Fibonacci.pm) |
| Boldi Vigna Zeta Coded Integer | ❌ | X | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/BoldiVigna.pm) |

### Large Integer Primitives

| Primitive            | Implemented | Filename | Example (if not implemented) |
|--------------------:|:-----------:|--------------|:-----------------------------|
| Big Unsigned Integer | ❌          | X            | X                            |
| Big Integer          | ❌          | X            | X                            |
| Big Decimal          | ❌          | X            | X                            |

### Array Primitives

| Primitive      | Implemented | Filename | Example (if not implemented) |
|-------------:|:-----------:|--------------|:-----------------------------|
| Array         | ❌          | X            | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/BLVec.pm) |
| Indexed Array | ❌          | X            | X                            |
| Sparse Array  | ❌          | X            | X                            |

### Symbol Primitives

| Primitive              | Implemented | Filename                                           | Example (if not implemented) |
|----------------------:|:-----------:|--------------------------------------------------------|:-----------------------------|
| Smaz Coded String      | ❌          | X                                                      | [Example](https://github.com/antirez/smaz) |
| Varicode String        | ❌          | X                                                      | [Example](https://github.com/argilo/gr-ham/blob/master/python/varicode_rx.py) |
| Morse Coded String     | ❌          | X                                                      | [Example](https://github.com/SeunMatt/morsecodetranslator/blob/master/src/main/java/com/smatt/morse/MorseCodeTranslator.java) |
| Variable Name Coded String | ❌      | X                                                      | X                            |
| Symbol Table           | ❌          | X                                                      | X                            |
| Trie                   | ❌          | X                                                      | X                            |

### Object Primitives

| Primitive    | Implemented | Filename | Example (if not implemented) |
|------------:|:-----------:|--------------|:-----------------------------|
| Map SerDe    | ❌          | X            | X                            |
| Object SerDe | ❌          | X            | X                            |

### Statistical and Adaptive Encoding

| Feature                          | Implemented |      Filename | Example (if not implemented) |
|-----------------------------------|:-----------:|--------------|:-----------------------------|
| Shannon Fano Coding and SerDe     | ❌          | X            | [Example](https://github.com/jigar23/Entropycoding/tree/master/EntropyCoding/src/ShannonFano) |
| Tunstall Coding and SerDe         | ❌          | X            | X                            |
| Huffman Tree Coding and SerDe     | ❌          | X            | [Example](https://github.com/jigar23/Entropycoding/tree/master/EntropyCoding/src/Huffman) |
| Arithmetic Encoding and SerDe     | ❌          | X            | [Example](https://github.com/nayuki/Reference-arithmetic-coding/blob/master/java/src/ArithmeticCoderBase.java) |
| ANS Encoding and SerDe            | ❌          | X            | [Example](https://github.com/EhomeBurning/Asymmetric-Numeral-Systems/blob/master/main.cpp) |
| Start-Stop Encoding and SerDe     | ❌          | X            | [Example](https://github.com/danaj/BitStream/blob/master/lib/Data/BitStream/Code/StartStop.pm) |
| Delta Encoding and SerDe          | ❌          | X            | [Example](https://en.wikipedia.org/wiki/Delta_encoding) |
| Range Encoding and SerDe          | ❌          | X            | [Example](https://github.com/apronchenkov/RangeCode/blob/master/coder.h) |
| Roaring Bitmaps and SerDe         | ❌          | X            | [Example](https://github.com/RoaringBitmap/RoaringBitmap) |

### Contextual Encoding

| Feature                          | Implemented |      Filename | Example (if not implemented) |
|-----------------------------------|:-----------:|--------------|:-----------------------------|
| CABAC and SerDe                  | ❌          | X            | [Example](https://github.com/jigar23/CABAC) |
| BWT and SerDe (Regular, SuffixTree)| ❌        | X            | [Example](https://github.com/egonelbre/fm-index/blob/master/src/bwt.py) |
| ACB and SerDe                    | ❌          | X            | [Example](http://www.stringology.org/DataCompression/acb/index_en.html) |
| DMC and SerDe                    | ❌          | X            | [Example](https://github.com/sporkmonger/squish/blob/master/src/lib/state.ml) |
| CTW and SerDe                    | ❌          | X            | [Example](https://github.com/gabeschamberg/context-tree-weighting/blob/master/ctw.py) |
| PPM and SerDe                    | ❌          | X            | [Example](https://github.com/gosoares/ITI-PPM/blob/master/PpmEncoder.kt) |
| RLE and SerDe                    | ❌          | X            | [Example](https://github.com/powturbo/TurboRLE) |
| MTF and SerDe                    | ❌          | X            | [Example](https://github.com/liam60/MTFEncoder/blob/master/WordList.java) |

### Dictionary Encoding

| Feature                        | Implemented | Filename | Example (if not implemented) |
|----------------------------:|:-----------:|--------------|:-----------------------------|
| Byte Pair Encoding and SerDe   | ❌          | X            | [Example](https://github.com/soaxelbrooke/python-bpe) |
| N-Gram Coding and SerDe        | ❌          | X            | [Example](https://github.com/yassineameur/one_hot_encoder) |
| LZ Encoding and SerDe          | ❌          | X            | [Example](https://github.com/gusrsilva/Lempel-Ziv-Welch-Compression/blob/master/src/ZivLempel.java) |
| ZStandard and SerDe            | ❌          | X            | [Example](https://github.com/noop-dev/Zstandard/blob/master/java/src/main/java/rtmath/zstd/ZstdDecompressor.java) |
| Snappy Encoding and SerDe      | ❌          | X            | [Example](https://github.com/dain/snappy/blob/master/src/main/java/org/iq80/snappy/SnappyCompressor.java) |
| Brotli and SerDe               | ❌          | X            | [Example](https://github.com/dominikhlbg/BrotliHaxe/blob/master/python/brotli.py) |

### Iterators

| Feature              | Implemented | Filename | Example (if not implemented) |
|--------------------:|:-----------:|--------------|:-----------------------------|
| ZigZag Iterator      | ❌          | X            | X                            |
| Slurm Iterator       | ❌          | X            | X                            |
| Interleaving Iterator| ❌          | X            | X                            |

### Lossy Encoding

| Feature                              | Implemented | Filename | Example (if not implemented) |
|------------------------------------:|:-----------:|--------------|:-----------------------------|
| Dimensional Sampler and SerDe         | ❌          | X            | X                            |
| Eigenvalue Sampler and SerDe          | ❌          | X            | X                            |
| HyperLogLog Sampler and SerDe         | ❌          | X            | X                            |
| Dynamic Range Transformer and SerDe   | ❌          | X            | X                            |
| Differencial Transformer and SerDe    | ❌          | X            | X                            |
| Exponential Range Transformer and SerDe| ❌         | X            | X                            |
| Clustering Transformer and SerDe      | ❌          | X            | X                            |
| Banding Transformer and SerDe         | ❌          | X            | X                            |
| Scalar Quantizer and SerDe            | ❌          | X            | X                            |
| Vector Quantizer and SerDe            | ❌          | X            | X                            |
| Fractal Transformer and SerDe         | ❌          | X            | X                            |
| Periodic Wave Transformers and SerDe  | ❌          | X            | X                            |
| Polynomial Equation Quantizer and SerDe| ❌        | X            | X                            |
| Bloom Filter Quantizer and SerDe      | ❌          | X            | X                            |
| Regression / Learning Transformer and SerDe | ❌   | X            | X                            |

### Specialized Data Encoding

| Feature                                   | Implemented | Filename | Example (if not implemented) |
|----------------------------------------:|:-----------:|--------------|:-----------------------------|
| Color Format Convertor                     | ❌          | X            | X                            |
| Wave Generator                            | ❌          | X            | X                            |
| Motion Predictor                          | ❌          | X            | X                            |
| Background Value Finder                    | ❌          | X            | X                            |
| Shape Binner                              | ❌          | X            | X                            |
| Stream Delta Encoder                      | ❌          | X            | [Example](https://github.com/eerimoq/detools) |

