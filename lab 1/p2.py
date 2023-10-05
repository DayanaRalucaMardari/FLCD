# p2 solution

definition gcd(nr1, nr2):
  if (nr2 == 0):
    returns nr1;
  else:
    returns gcd(nr2, nr1 %% nr2);