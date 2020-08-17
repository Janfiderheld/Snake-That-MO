using UnityEngine;

public class TouchController : MonoBehaviour
{
    public static TouchController Instance = null;

    private Touch _touch;

    private bool _currrentlyTouched = false;

    private void Awake()
    {
        if(Instance == null)
        {
            Instance = this;
        } else if(Instance != this) {
            Destroy(this);
        }
    }

    void Update()
    {
        if(Input.touchCount > 0)
        {
            _currrentlyTouched = _touch.phase == TouchPhase.Began;
        } else
        {
            _currrentlyTouched = false;
        }

    }

    public bool IsCurrentlyTouched()
    {
        return _currrentlyTouched;
    }
}