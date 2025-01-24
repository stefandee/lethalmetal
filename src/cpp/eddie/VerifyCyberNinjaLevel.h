//---------------------------------------------------------------------------

#ifndef VerifyCyberNinjaLevelH
#define VerifyCyberNinjaLevelH
//---------------------------------------------------------------------------

#include "VerifyLevel.h"

class CLevel;

class VerifyCyberNinjaLevel : public VerifyLevel
{
  public: // c-d
    VerifyCyberNinjaLevel(CLevel* );
    ~VerifyCyberNinjaLevel();

  public: // ops
    bool Verify();

  protected:

  private:
    CLevel* mLevel;
};

#endif
