#include "CString.h"

namespace _COMMON_NS
{
    std::string CString::getClassName() const 
    { 
        return std::string( (const char*)_QUOTE(CString) );
    }

    void CString::log() const
    {
       std::cout << mString;
    }

    std::string CString::toString() const
    {
        return this->mString;
    }

    Integer CString::compare(CObject* pObj)
    {
        _TYPE_CHECK_OBJECT(CString*, pObj);
        
         CString* sPtr = (CString*)pObj;

        if (sPtr != NULL)
         {
             return (Integer)this->mString.compare(sPtr->mString);
         }
        return _NOT_EQUAL;
    }

    Integer CString::compare(const char* pStr)
    {
        CString s(pStr);

        return compare((CObject*)&s);
    }
 
    Boolean CString::compareIgnoreCase(const std::string& a, const std::string& b)
    {
        return std::equal(a.begin(), a.end(),
                        b.begin(), b.end(),
                        [](char a, char b) {
                          return tolower(a) == tolower(b);
                        });
    }

    void CString::append(const char* pStr)
    {
        this->append( CString(pStr) );
    }

    void CString::append(const CString& str)
    {
        mString.append(str.getString());
    }

    CObject* CString::clone() const
    {
        return new CString( this->mString );
    }
}