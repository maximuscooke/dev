#include "CDateTime.h"

namespace  _COMMON_NS
{
    void CDateTime::log() const
    {
        std::cout << CDateTime::asctime(&mLocalTime);
    }

    std::string CDateTime::toString() const
    {
        return std::string( CDateTime::asctime(&mLocalTime) );
    }

    CObject* CDateTime::clone() const
    {
        return new CDateTime( this->mTime );
    }
 
    Integer CDateTime::compare(CObject* pObj)
    {          
        return CDateTime::compare((CDateTime*)this, (CDateTime*)pObj );
    }

    void CDateTime::init (struct tm& t, int year, int month, int dayOfMonth, int hour, int minutes, int seconds, int dayLightSaving)
    {
        t.tm_year = year - _DATE_OFFSET;

        t.tm_mon = month;

        t.tm_mday = dayOfMonth;

        t.tm_hour = hour;

        t.tm_min = minutes;

        t.tm_sec = seconds;

        t.tm_isdst = dayLightSaving;
    }

    Integer CDateTime::compare(const CDateTime* a, const CDateTime* b)
    {
        _TYPE_CHECK_OBJECT(const CDateTime*, a);
        _TYPE_CHECK_OBJECT(const CDateTime*, b);

        if (a->getYear() > b->getYear())
        {
            return _GREATER_THAN;
        }
        else if (a->getYear() < b->getYear())
        {
            return _LESS_THAN;
        }
        else
        {
            if (a->getMonth() > b->getMonth())
            {
                return _GREATER_THAN;
            }
            else if (a->getMonth() < b->getMonth())
            {
                return _LESS_THAN;
            }
            else
            {
                if (a->getDayOfMonth() > b->getDayOfMonth())
                {
                    return _GREATER_THAN;
                }
                else if (a->getDayOfMonth() < b->getDayOfMonth())
                {
                    return _LESS_THAN;
                }
                else
                {
                    if (a->getHour() > b->getHour())
                    {
                        return _GREATER_THAN;
                    }
                    else if (a->getHour() < b->getHour())
                    {
                        return _LESS_THAN;
                    }
                    else
                    {
                        if (a->getMinutes() > b->getMinutes())
                        {
                            return _GREATER_THAN;
                        }
                        else if (a->getMinutes() < b->getMinutes())
                        {
                            return _LESS_THAN;
                        }
                        else
                        {
                            if (a->getSeconds() > b->getSeconds())
                            {
                                return _GREATER_THAN;
                            }
                            else if (a->getSeconds() < b->getSeconds())
                            {
                                return _LESS_THAN;
                            }
                        }
                    }
                }
            }
        }

        return _EQUAL;
    }
}