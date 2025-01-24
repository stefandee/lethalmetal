//---------------------------------------------------------------------------

#ifndef VerifyLevelH
#define VerifyLevelH
//---------------------------------------------------------------------------

#include <vector>
#include <string>

class VerifyLevel
{
  public:
    VerifyLevel();
    ~VerifyLevel();

  public: // ops
    virtual bool Verify() = 0;

  public: // list
    std::vector<std::string> GetErrors() { return ErrorsList(); }
    std::vector<std::string> GetWarnings() { return WarningsList(); }

    int GetErrorsCount() { return ErrorsList().size(); }
    int GetWarningsCount() { return WarningsList().size(); }

  protected:
    std::vector<std::string>& ErrorsList() { return mErrorsList; }
    std::vector<std::string>& WarningsList() { return mWarningsList; }

    void AddWarning(std::string _v) { mWarningsList.push_back(_v); }
    void AddError(std::string _v) { mErrorsList.push_back(_v); }

  private:
    std::vector<std::string> mErrorsList, mWarningsList;
};

#endif
