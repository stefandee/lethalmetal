//---------------------------------------------------------------------------

#ifndef MementoLevelH
#define MementoLevelH
//---------------------------------------------------------------------------

class CLevel;

class CMementoLevel
{
  public:
    CMementoLevel();
    ~CMementoLevel();

  public:
    const CLevel* GetState();
    void    SetState(CLevel*);

  protected:

  private:
    CLevel* mState;

    CMementoLevel(const CMementoLevel&);
    CMementoLevel& operator=(const CMementoLevel&);
};

#endif
