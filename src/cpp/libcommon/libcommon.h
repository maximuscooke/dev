#ifndef _COMMONLIB_HEADER_H_
#define _COMMONLIB_HEADER_H_

#include <iostream>

typedef long Long;
typedef char Char, Byte;
typedef unsigned long ULong, LIndex, LCount;
typedef unsigned char UChar, UByte;
typedef short int Short;
typedef unsigned short UShort;
typedef float Float;
typedef double Double;
typedef long double LDouble;
typedef int Int, Integer;
typedef unsigned int UInteger, UInt, Index, Count;
typedef float Real;
typedef bool Boolean, Bool;
typedef const char* stringPtr;
typedef const void* Ptr;

#define _COMMON_NS common

#define _OVERRIDE_MSG "Override in derived class."

#define _ABSTRACT =0
#define _VIRTUAL virtual
#define _OVERRIDE override
#define _IMPLEMENT virtual
#define _PLATFORM "Platform Un-Specified"
#define _INLINE inline
#define _STATIC static
#define _INLINE_STATIC inline static

#define _ASCENDING  1
#define _DESCENDING 2

#define _SUNDAY     0
#define _MONDAY     1
#define _TUESDAY    2
#define _WEDNESDAY  3
#define _THURSDAY   4
#define _FRIDAY     5
#define _SATURDAY   6

#define _JANUARY    0
#define _FEBRUARY   1
#define _MARCH      2
#define _APRIL      3
#define _MAY        4
#define _JUNE       5
#define _JULY       6
#define _AUGUST     7
#define _SEPTEMBER  8
#define _OCTOBER    9
#define _NOVEMBER   10
#define _DECEMBER   11

#define FILE_MODE_READ_BIT 0
#define FILE_MODE_WRITE_BIT 1
#define FILE_MODE_APPEND_BIT 2
#define FILE_MODE_BINARY_BIT 3

#define _EQUAL           0
#define _LESS_THAN      -1
#define _GREATER_THAN    1
#define _NOT_EQUAL      -2

#define _MAX_FILE_PATH 256;

enum CompareResult : int { LessThan=_LESS_THAN, Equal=_EQUAL, GreaterThan=_GREATER_THAN, NotEqual=_NOT_EQUAL };
enum DayOfWeek : int { Sunday=0, Monday=1, Tuesday=2, Wednesday=3, Thursday=4, Friday=5, Saturday=6 };
enum Month : int { January=_JANUARY, February=_FEBRUARY, March=_MARCH, April=_APRIL, May=_MAY, June=_JUNE, July=_JULY, August=_AUGUST, September=_SEPTEMBER, October=_OCTOBER, November=_NOVEMBER, December=_DECEMBER};
enum SortOrder : int { Ascending=_ASCENDING, Descending=_DESCENDING };
enum FileMode : int { Read = (1<<FILE_MODE_READ_BIT), Write = (1<<FILE_MODE_WRITE_BIT), Append = (1<<FILE_MODE_APPEND_BIT), Binary = (1<<FILE_MODE_BINARY_BIT) };

#ifdef _MAC
#undef _PLATFORM
#define _PLATFORM "Mac-Os"
#endif

#ifdef _WIN
#undef _PLATFORM
#define _PLATFORM "Windows"
#endif

#define _QUOTE(_str) (#_str)

#define _COMPARE_ORDER(x,y) (x == y) ? _EQUAL : ( x < y) ? _LESS_THAN : _GREATER_THAN

#ifdef DEBUG
#define _LOG(X) std::cerr << "[" << __TIME__ << "] " << X << " (" << __FILE__ <<  ", Ln " << __LINE__ << ")" << std::endl
#define _LOGF(...) std::cerr << "[" << __TIME__ << "] "; fprintf(stderr, __VA_ARGS__); std::cerr  << " (" << __FILE__ <<  ", Ln " << __LINE__ << ")" << std::endl
#else
#define _LOG(X)
#define _LOGF(...)
#endif

#ifdef SUPPRESS_TYPE_CHECK
#define _TYPE_CHECK_OBJECT(type, obj)
#else
#define _TYPE_CHECK_OBJECT(type, obj) if(!dynamic_cast<type>(obj)) std::cerr << "Warning: Class type mismatch" << std::endl
#endif

#define _BIT_TEST(op, bit)   (op &  (1<<bit))
#define _BIT_SET(op, bit)    (op |= (1<<bit))
#define _BIT_CLEAR(op, bit)  (op &= ~(1<<bit))
#define _BIT_FLIP(op, bit)   (op ^= (1<<bit))

#define _BIT_PRINT(op, bitcount) std::cout << std::bitset<bitcount>(op) << std::endl

#define _GET_PROPERTY(type, name, var) inline type get##name() const { return var; } 
#define _SET_PROPERTY(type, name, var) inline void set##name(type v) { var = v; }

#define _PROPERTY(type, name, var) inline type get##name() const { return var; } inline void set##name(type v) { var = v; }

#define _OP_NUMERIC(cType, opType, var) void    operator+(opType _val_)     { this->var += _val_; } \
                                        void    operator-(opType _val_)     { this->var -= _val_; } \
                                        void    operator*(opType _val_)     { this->var *= _val_; } \
                                        void    operator/(opType _val_)     { this->var /= _val_; } \
                                        cType   operator=(opType _val_)     { return cType(_val_); } \
                                        cType   operator+(const cType& op)  { cType _r; _r.var = this->var + op.var; return _r; } \
                                        cType   operator-(const cType& op)  { cType _r; _r.var = this->var - op.var; return _r; } \
                                        cType   operator*(const cType& op)  { cType _r; _r.var = this->var * op.var; return _r; } \
                                        cType   operator/(const cType& op)  { cType _r; _r.var = this->var / op.var; return _r; } \
                                        Bool    operator>=(const cType& op) { Integer iResult; return (iResult = compare( (cType*)&op)) == _GREATER_THAN || iResult == _EQUAL  ? true : false ; } \
                                        Bool    operator<=(const cType& op) { Integer iResult; return (iResult = compare( (cType*)&op)) == _LESS_THAN || iResult == _EQUAL  ? true : false ; } \
                                        Bool    operator!=(const cType& op) { return compare( (cType*)&op) == _EQUAL ? false : true ; } \
                                        Bool    operator>(const cType& op)  { return compare( (cType*)&op) == _GREATER_THAN ? true : false ; } \
                                        Bool    operator<(const cType& op)  { return compare( (cType*)&op) == _LESS_THAN ? true : false ; } \
                                        Bool    operator==(const cType& op) { return compare( (cType*)&op) == _EQUAL ? true: false; }


#define _OP_NUMERIC_MOD(cType, opType, var)  void operator%(opType _val_) { this->var %= _val_; } \
                                            cType operator%(const cType& op) { cType __r; __r.var = this->var % op.var; return __r; }

#define _GET_CLASS_NAME(x) _OVERRIDE std::string getClassName() const { return std::string( (const char*)_QUOTE(x) ); }

#define _ERR_MSG_KEY_NOT_FOUND "Key Not Found"

#endif
