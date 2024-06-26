#  Copyright (C) nubit.in.


-optimizationpasses 5
-dontpreverify
-repackageclasses ''
-allowaccessmodification
-optimizations !code/simplification/arithmetic
-keepattributes *Annotation*

-verbose

-dump obfuscation/class_files.txt
-printseeds obfuscation/seeds.txt
-printusage obfuscation/unused.txt
-printmapping obfuscation/mapping.txt






  # Keep native methods
  -keepclassmembers class * {
      native <methods>;
  }






   -keepclassmembers class com.skd.nubit.** {
          *;
       }



