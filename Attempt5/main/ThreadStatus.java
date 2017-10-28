public enum ThreadStatus {
    NON_CRITICAL_SECTION        ("-> In the nonCritical section"),
    PRE_PROTOCOL                ("-> In the pre protocol section"),
    LEAVING_PRE_PROTOCOL        ("<- Leaving the pre protocol section"),
    ENTERING_CRITICAL_SECTION   ("-> Entering the critical section"),
    CRITICAL_SECTION            ("-> In the critical section"),
    LEAVING_CRITICAL_SECTION    ("<- Leaving the critical section"),
    POST_PROTOCOL               ("-> In the post protocol section");

    private final String status;

    ThreadStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return status;
    }
}
