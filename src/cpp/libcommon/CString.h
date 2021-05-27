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

            _STATIC _INLINE std::string convert(Int val)     { return std::to_string( val ); }
            _STATIC _INLINE std::string convert(Double val)  { return std::to_string( val ); }
            _STATIC _INLINE std::string convert(Float val)   { return std::to_string( val ); }
            _STATIC _INLINE std::string convert(Long val)    { return std::to_string( val ); }
            _STATIC _INLINE std::string convert(Short val)   { return std::to_string( val ); }
            _STATIC _INLINE std::string convert(Byte val)    { return std::to_string( val ); }

            _STATIC _INLINE Integer  parseToInteger(std::string op)      { return std::stoi( op); }
            _STATIC _INLINE Integer  parseToInteger(const Char* op)      { return std::stoi( op); }
            _STATIC _INLINE Long     parseToLong(std::string op)     { return std::stol( op); }
            _STATIC _INLINE Long     parseToLong(const Char* op)     { return std::stol( op); }
            _STATIC _INLINE Float    parseToFloat(std::string op)    { return std::stof( op); }
            _STATIC _INLINE Float    parseToFloat(const Char* op)    { return std::stof( op); }
            _STATIC _INLINE Double   parseToDouble(std::string op)   { return std::stod( op); }
            _STATIC _INLINE Double   parseToDouble(const Char* op)   { return std::stod( op); }

            _STATIC _INLINE Integer  isAlphaNumeric(Int c)       { return ::isalnum( c ); }
            _STATIC _INLINE Integer  isAlpha(Int c)              { return ::isalpha( c ); }
            _STATIC _INLINE Integer  isBlank(Int c)              { return ::isblank( c ); }
            _STATIC _INLINE Integer  isControlCharacter(Int c)   { return ::iscntrl( c); }
            _STATIC _INLINE Integer  isDigit(Int c)              { return ::isdigit( c ); }
            _STATIC _INLINE Integer  isGraphical(Int c)          { return ::isgraph( c ); }
            _STATIC _INLINE Integer  isLowerCase(Int c)          { return ::islower( c ); }
            _STATIC _INLINE Integer  isPrintable(Int c)          { return ::isprint( c ); }
            _STATIC _INLINE Integer  isPunctuation(Int c)        { return ::ispunct( c ); }
            _STATIC _INLINE Integer  isSpace(Int c)              { return ::isspace( c ); }
            _STATIC _INLINE Integer  isUpperCase(Int c)          { return ::isupper( c ); }
            _STATIC _INLINE Integer  isHexDigit(Int c)           { return ::isxdigit( c ); }
            _STATIC _INLINE Integer  toLowerCase(Int c)          { return ::tolower( c ); }
            _STATIC _INLINE Integer  toUpperCase(Int c)          { return ::toupper( c ); }

            _STATIC _INLINE char*    strcat(char* destination, const char* source)           { return ::strcat(destination, source); }
            _STATIC _INLINE Integer  strcmp(const char* str1, const char* str2)              { return ::strcmp(str1, str2); }
            _STATIC _INLINE Integer  strncmp(const char* str1, const char* str2, size_t num) { return ::strncmp(str1, str2, num); }
            _STATIC _INLINE size_t   strlen(const char* str)                                 { return ::strlen(str); }

            _STATIC Boolean  compareIgnoreCase(const char* pStr1, const char* pStr2) { return compareIgnoreCase( CString(pStr1), CString(pStr2) ); }
            _STATIC Boolean  compareIgnoreCase(const CString& a, const CString& b)   {  return compareIgnoreCase(a.mString, b.mString); }
            _STATIC Boolean  compareIgnoreCase(const std::string& a, const std::string& b);

            // Overriden Member methods
            _OVERRIDE std::string toString() const;
            _OVERRIDE void log() const;
            _OVERRIDE CObject* clone() const;
            _OVERRIDE Integer compare(CObject* pObj);
            _OVERRIDE std::string getClassName() const;
  
            // Member Methods
            void append(const CString& str);
            void append(const char* pStr);

            void toUpper() { std::transform(mString.begin(), mString.end(),mString.begin(), ::toupper); }
            void toLower() { std::transform(mString.begin(), mString.end(),mString.begin(), ::tolower); }
 
            std::string substring(std::size_t pos, std::size_t length) { return mString.substr(pos, length); }
            std::string substring(std::size_t pos) { return mString.substr(pos); }
  
            Integer compare(const char* pStr);
            Integer compare(const CString& str)             { return (Integer)this->mString.compare(str.mString); }
            Boolean compareIgnoreCase(const CString& str)   { return compareIgnoreCase(this->mString, str.mString); }
            Boolean compareIgnoreCase(const char* pStr)     { return compareIgnoreCase( CString(pStr) ); }

            // Operator overloads
            Integer operator==(const CString& _val) { return compare( (CObject*)&_val); };
            Integer operator==(const char* pStr)    { return compare( pStr ); }
            CString operator=(const char* pStr)     { return CString(pStr); } 
            CString operator=(const CString& _val)  { return CString(_val.getString() ); } 
            CString operator+(const CString& _val)  { CString s(this->getString()); s.append(_val); return s; }
            CString operator+(const char* pStr)     { CString s(this->getString()); s.append(pStr); return s; }
 
            // Properties
            std::string getString() const { return mString; }

            std::size_t getLength() { return this->mString.length(); }

            _PROPERTY(std::string , Value, mString)
    };
}
#endif