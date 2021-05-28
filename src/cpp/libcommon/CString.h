#ifndef _CSTRING_H_
#define _CSTRING_H_

#include "CObject.h"
#include <string>

namespace  _COMMON_NS
{
    class CString : public CObject
    {
        private:
             std::string mString; 

        public:
            CString(std::string str)
            {
                mString = str;
            }

            CString(const char* pString)
            {
                mString = std::string( pString );
            }

            _INLINE_STATIC std::string convert(Int val)     { return std::to_string( val ); }
            _INLINE_STATIC std::string convert(Double val)  { return std::to_string( val ); }
            _INLINE_STATIC std::string convert(Float val)   { return std::to_string( val ); }
            _INLINE_STATIC std::string convert(Long val)    { return std::to_string( val ); }
            _INLINE_STATIC std::string convert(Short val)   { return std::to_string( val ); }
            _INLINE_STATIC std::string convert(Byte val)    { return std::to_string( val ); }

            _INLINE_STATIC Integer  parseToInteger(std::string op)      { return std::stoi( op); }
            _INLINE_STATIC Integer  parseToInteger(const Char* op)      { return std::stoi( op); }
            _INLINE_STATIC Long     parseToLong(std::string op)     { return std::stol( op); }
            _INLINE_STATIC Long     parseToLong(const Char* op)     { return std::stol( op); }
            _INLINE_STATIC Float    parseToFloat(std::string op)    { return std::stof( op); }
            _INLINE_STATIC Float    parseToFloat(const Char* op)    { return std::stof( op); }
            _INLINE_STATIC Double   parseToDouble(std::string op)   { return std::stod( op); }
            _INLINE_STATIC Double   parseToDouble(const Char* op)   { return std::stod( op); }

            _INLINE_STATIC Integer  isAlphaNumeric(Int c)       { return ::isalnum( c ); }
            _INLINE_STATIC Integer  isAlpha(Int c)              { return ::isalpha( c ); }
            _INLINE_STATIC Integer  isBlank(Int c)              { return ::isblank( c ); }
            _INLINE_STATIC Integer  isControlCharacter(Int c)   { return ::iscntrl( c); }
            _INLINE_STATIC Integer  isDigit(Int c)              { return ::isdigit( c ); }
            _INLINE_STATIC Integer  isGraphical(Int c)          { return ::isgraph( c ); }
            _INLINE_STATIC Integer  isLowerCase(Int c)          { return ::islower( c ); }
            _INLINE_STATIC Integer  isPrintable(Int c)          { return ::isprint( c ); }
            _INLINE_STATIC Integer  isPunctuation(Int c)        { return ::ispunct( c ); }
            _INLINE_STATIC Integer  isSpace(Int c)              { return ::isspace( c ); }
            _INLINE_STATIC Integer  isUpperCase(Int c)          { return ::isupper( c ); }
            _INLINE_STATIC Integer  isHexDigit(Int c)           { return ::isxdigit( c ); }
            _INLINE_STATIC Integer  toLowerCase(Int c)          { return ::tolower( c ); }
            _INLINE_STATIC Integer  toUpperCase(Int c)          { return ::toupper( c ); }

            _INLINE_STATIC char*    strcat(char* destination, const char* source)           { return ::strcat(destination, source); }
            _INLINE_STATIC Integer  strcmp(const char* str1, const char* str2)              { return ::strcmp(str1, str2); }
            _INLINE_STATIC Integer  strncmp(const char* str1, const char* str2, size_t num) { return ::strncmp(str1, str2, num); }
            _INLINE_STATIC size_t   strlen(const char* str)                                 { return ::strlen(str); }

            _STATIC Boolean  compareIgnoreCase(const char* pStr1, const char* pStr2) { return compareIgnoreCase( CString(pStr1), CString(pStr2) ); }
            _STATIC Boolean  compareIgnoreCase(const CString& a, const CString& b)   {  return compareIgnoreCase(a.mString, b.mString); }
            _STATIC Boolean  compareIgnoreCase(const std::string& a, const std::string& b);

            // Overriden Member methods
            std::string toString() const _OVERRIDE;
            void log() const _OVERRIDE;
            CObject* clone() const _OVERRIDE;
            Integer compare(CObject* pObj) _OVERRIDE;
            std::string getClassName() const _OVERRIDE;
  
            // Member Methods
            void append(const CString& str);
            void append(const char* pStr);

            void toUpper() { std::transform(mString.begin(), mString.end(),mString.begin(), ::toupper); }
            void toLower() { std::transform(mString.begin(), mString.end(),mString.begin(), ::tolower); }
 
            _INLINE std::string substring(std::size_t pos, std::size_t length) { return mString.substr(pos, length); }
            _INLINE std::string substring(std::size_t pos) { return mString.substr(pos); }
  
            Integer compare(const char* pStr);
            _INLINE Integer compare(const CString& str)            { return (Integer)this->mString.compare(str.mString); }
            _INLINE Boolean compareIgnoreCase(const CString& str)   { return compareIgnoreCase(this->mString, str.mString); }
            _INLINE Boolean compareIgnoreCase(const char* pStr)     { return compareIgnoreCase( CString(pStr) ); }

            // Operator overloads
            Integer operator==(const CString& _val) { return compare( (CObject*)&_val); };
            Integer operator==(const char* pStr)    { return compare( pStr ); }
            CString operator=(const char* pStr)     { return CString(pStr); } 
            CString operator=(const CString& _val)  { return CString(_val.getString() ); } 
            CString operator+(const CString& _val)  { CString s(this->getString()); s.append(_val); return s; }
            CString operator+(const char* pStr)     { CString s(this->getString()); s.append(pStr); return s; }
 
            // Properties
            _INLINE std::string getString() const { return mString; }

            _INLINE std::size_t getLength() { return this->mString.length(); }

            _PROPERTY(std::string , Value, mString)
    };
}
#endif