#ifndef _CDATETIME_H_
#define _CDATETIME_H_

#include "CObject.h"
#include <time.h>

#define _DATE_OFFSET 1900

namespace  _COMMON_NS
{
    class CDateTime : public CObject
    {
        private:
            time_t mTime;
            struct tm mLocalTime;

        public:
            CDateTime()
            {
                this->setTime( time() );
            }

            CDateTime(const time_t t)
            {
                this->setTime(t);
            }

            CDateTime(int year, int month, int dayOfMonth, int hour = 0, int minutes = 0, int seconds = 0, int dayLightSaving = -1)
            {
                struct tm t;

                init(t, year, month, dayOfMonth, hour, minutes, seconds, dayLightSaving);

                this->setTime( mktime(&t) );
            }

            _INLINE_STATIC time_t time() { return time(NULL); }
            _INLINE_STATIC time_t time(time_t* t) { return ::time(t); }

            _INLINE_STATIC struct tm* localtime(const time_t* t) { return ::localtime(t); }
            _INLINE_STATIC struct tm* localtime() { time_t t = time(); return localtime(&t); }

            _INLINE_STATIC char* asctime(const struct tm* timePtr) { return ::asctime(timePtr); }
            _INLINE_STATIC char* asctime() { return asctime( localtime() ); }

            _INLINE_STATIC struct tm* gmtime(const time_t* timer) { return ::gmtime(timer); }
            _INLINE_STATIC struct tm* gmtime() { time_t t = time(); return gmtime( &t); }

            _INLINE_STATIC char* ctime(const time_t* timer) { return ::ctime(timer); }
            _INLINE_STATIC char* ctime() { time_t t = time(); return ctime(&t); }

            _INLINE_STATIC time_t mktime(struct tm* timePtr) { return ::mktime(timePtr); }
            _INLINE_STATIC time_t mktime() { struct tm* tPtr = localtime(); return ::mktime(tPtr); }

            _INLINE_STATIC size_t strftime (char* ptr, size_t maxsize, const char* format, const struct tm* timeptr ) { return ::strftime(ptr, maxsize, format, timeptr ); }

            _INLINE_STATIC Double difftime (time_t end, time_t beginning) { return ::difftime(end, beginning);}

            _INLINE_STATIC clock_t clock() { return ::clock(); }

            _INLINE_STATIC Integer compare(const CDateTime* a, const CDateTime* b);

            _STATIC void init(struct tm& t, int year, int month = 0, int dayOfMonth = 0, int hour = 0, int minutes = 0, int seconds = 0, int dayLightSaving = -1);

            Integer operator==(const CDateTime& obj) { return compare( (CObject*)&obj); }

            std::string toString() const _OVERRIDE;
            void log() const _OVERRIDE;
            CObject* clone() const _OVERRIDE;
            Integer compare(CObject* pObj) _OVERRIDE;
            std::string getClassName() const _OVERRIDE;

            _INLINE Integer getYear()       const { return mLocalTime.tm_year + _DATE_OFFSET; }
            _INLINE Integer getMonth()      const { return mLocalTime.tm_mon; }
            _INLINE Integer getDayOfMonth() const { return mLocalTime.tm_mday; }
            _INLINE Integer getHour()       const { return mLocalTime.tm_hour; }
            _INLINE Integer getMinutes()    const { return mLocalTime.tm_min; }
            _INLINE Integer getSeconds()    const { return mLocalTime.tm_sec; }
            _INLINE Integer getWeekDay()    const { return mLocalTime.tm_wday; }
            _INLINE Integer getYearDay()    const { return mLocalTime.tm_yday; }
            _INLINE char*   getTimeZone()   const { return mLocalTime.tm_zone; }
  
            _INLINE time_t getTime() { return mTime; }
            void setTime(time_t t);

            _INLINE struct tm getLocalTime() { return this->mLocalTime; }
    };
}
#endif